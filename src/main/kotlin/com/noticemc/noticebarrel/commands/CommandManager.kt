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

package com.noticemc.noticebarrel.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Single
import co.aikar.commands.annotation.Subcommand
import com.github.shynixn.mccoroutine.launch
import com.noticemc.noticebarrel.NoticeBarrel.Companion.plugin
import com.noticemc.noticebarrel.api.QuickShop
import com.noticemc.noticebarrel.files.Config
import kotlinx.coroutines.delay
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Barrel
import org.bukkit.block.Chest
import org.bukkit.block.data.Directional
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

@CommandAlias("nb|NoticeBarrel")
class CommandManager : BaseCommand() {

    @CommandPermission("NoticeBarrel.changeBarrel")
    @Subcommand("change")
    fun enableChangeBarrel(sender: CommandSender) {
        val player: Player = sender as Player
        if (canChangeBarrel[player.uniqueId] == null || canChangeBarrel[player.uniqueId] == false) {
            canChangeBarrel[player.uniqueId] = true
            player.sendMessage("ChangeBarrelモードが有効です")
        } else {
            canChangeBarrel[player.uniqueId] = false
            player.sendMessage("ChangeBarrelモードを無効にしました")
        }
    }

    @CommandPermission("NoticeBarrel.reload")
    @Subcommand("reload")
    fun reload(sender: CommandSender) {
        sender.sendMessage("NoticeBarrelを再読み込みしました")
        Config.load()
    }

    @CommandPermission("NoticeBarrel.barrelsPit")
    @Subcommand("pit")
    fun barrelsPit(sender: CommandSender, @Single radius: Int) {
        plugin!!.launch {
            val player: Player = sender as Player
            if (radius !in 1..1024) {
                player.sendMessage("半径は1-1024である必要があります")
                return@launch
            }
            val chestList = scan(player, radius)
            sender.sendMessage("${chestList.size}個のチェストを発見しました")
            var count = 0
            chestList.forEach {
                val direction = (it.block.blockData as Directional).facing
                val chestInventory: Inventory = (it.block.state as Chest).blockInventory
                val items: Array<out ItemStack?>? = chestInventory.contents
                it.block.type = Material.BARREL
                val barrel: Barrel = (it.block.state as Barrel)
                val afterBlock = it.block
                val afterBlockData = afterBlock.blockData
                barrel.inventory.contents = items
                (afterBlockData as Directional).facing = direction
                afterBlock.blockData = afterBlockData
                count++
                if (count % 20 == 0) {
                    player.sendMessage("${count}個のチェストを登録しました")
                    delay(100)
                }
            }
            player.sendMessage("${count}個のチェストを登録しました")
        }
    }

    private suspend fun scan(player: Player, radius: Int): Set<Location> {
        val x = player.location.blockX
        val z = player.location.blockZ

        val items = HashSet<Location>()
        var count = 0

        for (i in -radius..radius) {
            for (j in -64..319) {
                for (k in -radius..radius) {
                    val loc = Location(player.world, (x + i).toDouble(), j.toDouble(), (z + k).toDouble())
                    if (count % 1000000 == 0) {
                        delay(100)
                        player.sendMessage("${items.size}個のチェストをスキャンされました")
                    }
                    if (loc.block.type == Material.CHEST || loc.block.type == Material.TRAPPED_CHEST) {
                        if (QuickShop.getQuickShopAPI()!!.shopManager.getShop(loc) == null) {
                            items.add(loc)
                        }
                    }
                    count++
                }
            }
        }
        return items
    }

    companion object {
        var canChangeBarrel: HashMap<UUID, Boolean> = HashMap()
    }
}