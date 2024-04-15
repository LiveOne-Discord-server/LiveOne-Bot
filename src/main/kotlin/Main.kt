package com.liveOne.github.botDS

import com.liveOne.github.botDS.commands.*
import com.liveOne.github.botDS.configuration.MConfig.config
import com.liveOne.github.botDS.configuration.MConfig.saveConfiguration
import net.dv8tion.jda.api.*
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.interactions.commands.build.*

fun main() {
    saveConfiguration()
    load() }

fun load() {
    val bot: JDA = JDABuilder.createLight(config.getString("Token")).addEventListeners(CMDUsers())
        .setStatus(OnlineStatus.DO_NOT_DISTURB)
        .setActivity(Activity.watching(config.getString("Activity-Text")))
        .build()
    bot.updateCommands().addCommands(
        Commands.slash("about", "about discord server").setGuildOnly(true)
    ).queue()

    bot.awaitReady()
}