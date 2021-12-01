package com.noticemc.noticebarrel.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class CommandManager : BaseCommand() {

    @CommandAlias("cb|changeBarrel")
    fun enableChangeBarrel(sender:CommandSender) {
        val player: Player = sender as Player
        if(canChangeBarrel[player.uniqueId] == null || canChangeBarrel[player.uniqueId] == false) {
            canChangeBarrel[player.uniqueId] = true;
            player.sendMessage("ChangeBarrelモードが有効です")
        }else {
            canChangeBarrel[player.uniqueId] = false
            player.sendMessage("ChangeBarrelモードを無効にしました")
        }
    }

    companion object {
        var canChangeBarrel: HashMap<UUID, Boolean> = HashMap()
    }
}