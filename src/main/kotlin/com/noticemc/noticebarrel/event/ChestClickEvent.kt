/*
 * Copyright © 2021 Nikomaru
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.noticemc.noticebarrel.event

import com.noticemc.noticebarrel.api.GriefPrevention
import com.noticemc.noticebarrel.api.QuickShop
import com.noticemc.noticebarrel.commands.CommandManager
import me.ryanhamshire.GriefPrevention.ClaimPermission
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Barrel
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

internal class ChestClickEvent : Listener {

    @EventHandler
    fun chestClickEvent(event: PlayerInteractEvent) {
        val player: Player = event.player
        val clickedBlock = event.clickedBlock ?: return
        if (CommandManager.canChangeBarrel[player.uniqueId] == null || CommandManager.canChangeBarrel[player.uniqueId] == false
            || clickedBlock.type != Material.CHEST || event.action == Action.RIGHT_CLICK_BLOCK) {
            return
        }

        if(GriefPrevention.GriefPreventionAPI() != null) {
            if (GriefPrevention.GriefPreventionAPI()!!.getClaimAt(clickedBlock.location, true, null)
                    ?.hasExplicitPermission(player, ClaimPermission.Build) != true && !player.hasPermission("NoticeBarrel.admin")) {
                player.sendMessage(MiniMessage.get().parse("<red>You don't have permission to change this chest."))
                return
            }
        }

        if (QuickShop.getQuickShopAPI() != null) {
            if (QuickShop.getQuickShopAPI()!!.shopManager.getShop(clickedBlock.location) != null) {
                player.sendMessage(MiniMessage.get().parse("<red>You can't change this shop  chest."))
                return
            }
        }

        val chestInventory: Inventory = (clickedBlock.state as Chest).blockInventory
        val items: Array<out ItemStack?>? = chestInventory.contents
        val locate: Location = clickedBlock.location
        clickedBlock.type = Material.BARREL
        val barrel: Barrel = (locate.block.state as Barrel)
        barrel.inventory.contents = items
    }
}