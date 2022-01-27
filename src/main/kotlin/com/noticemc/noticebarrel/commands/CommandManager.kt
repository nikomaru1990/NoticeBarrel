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
import co.aikar.commands.annotation.Subcommand
import com.noticemc.noticebarrel.files.Config
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

@CommandAlias("nb|NoticeBarrel")
class CommandManager : BaseCommand() {

    @CommandPermission("NoticeBarrel.changeBarrel")
    @Subcommand("change")
    fun enableChangeBarrel(sender:CommandSender) {
        val player: Player = sender as Player
        if(canChangeBarrel[player.uniqueId] == null || canChangeBarrel[player.uniqueId] == false) {
            canChangeBarrel[player.uniqueId] = true
            player.sendMessage("ChangeBarrelモードが有効です")
        }else {
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

    companion object {
        var canChangeBarrel: HashMap<UUID, Boolean> = HashMap()
    }
}