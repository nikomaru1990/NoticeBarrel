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

import com.noticemc.noticebarrel.commands.CommandManager
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
        if (CommandManager.canChangeBarrel[player.uniqueId] == null || CommandManager.canChangeBarrel[player.uniqueId] == false) {
            return
        }
        if (event.clickedBlock == null) {
            return
        }
        if (event.clickedBlock!!.type != Material.CHEST) {
            return
        }
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            return
        }
        val chestInventory: Inventory = (event.clickedBlock!!.state as Chest).blockInventory
        val items: Array<out ItemStack?> = chestInventory.contents
        event.clickedBlock!!.type = Material.BARREL
        val barrel: Barrel = (event.clickedBlock!!.state as Barrel)
        barrel.inventory.contents = items
    }
}