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
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class ChestBreakEvent : Listener {
    @EventHandler
    fun onChestBreak(event: BlockBreakEvent) {
        if (CommandManager.canChangeBarrel[event.player.uniqueId] != true) return
        if (event.block.type == Material.CHEST || event.block.type == Material.TRAPPED_CHEST || event.block.type == Material.BARREL) {
            event.isCancelled = true
        }
    }
}