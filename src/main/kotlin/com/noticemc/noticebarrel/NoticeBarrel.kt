package com.noticemc.noticebarrel

import co.aikar.commands.PaperCommandManager
import com.noticemc.noticebarrel.commands.CommandManager
import com.noticemc.noticebarrel.event.ChestClickEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class NoticeBarrel : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        plugin = this
        val manager =PaperCommandManager(this)
        manager.registerCommand(CommandManager())
        Bukkit.getPluginManager().registerEvents(ChestClickEvent(),this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
    companion object{
        var plugin :NoticeBarrel? = null
    }
}