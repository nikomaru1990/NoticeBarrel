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

package com.noticemc.noticebarrel

import co.aikar.commands.PaperCommandManager
import com.noticemc.noticebarrel.commands.CommandManager
import com.noticemc.noticebarrel.event.ChestClickEvent
import com.noticemc.noticebarrel.files.Config
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class NoticeBarrel : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        plugin = this
        Config.load()
        if(Config.config?.node("plugin" , "griefPrevention")?.boolean == true) {
            if (!server.pluginManager.isPluginEnabled("GriefPrevention")) {
                logger.severe("GriefPrevention not found!")
                server.pluginManager.disablePlugin(this)
                return
            }
        }
        if(Config.config?.node("plugin" , "quickShop")?.boolean == true) {
            if (!server.pluginManager.isPluginEnabled("QuickShop")) {
                logger.severe("QuickShop not found!")
                server.pluginManager.disablePlugin(this)
                return
            }
        }

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