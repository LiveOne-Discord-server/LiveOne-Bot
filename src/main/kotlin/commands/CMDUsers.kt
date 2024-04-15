package com.liveOne.github.botDS.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.*
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.*

class CMDUsers: ListenerAdapter() {

    override fun onSlashCommandInteraction(e: SlashCommandInteractionEvent) {
        when (e.name) {
            "about" -> {
                val eb = EmbedBuilder()

                eb.setTitle("LiveOne")
                eb.addField("Про LiveOne",
                            "Цей **Discord** сервер був створенний для **ютубера [Baner](https://www.youtube.com/@BansaFanchik)**", false)

                eb.addField("Про бота",
                            "**Привіт!** Я був створенний для Discord серверу **LiveOne**\n" +
                                    "На данний час я знаходжусь ще на стадії **розроблення**, та працюю з дуже мало команд\n" +
                                    "Я буду давати інформацію через команди ;)\n", false)

                eb.setFooter("Official bot for LiveOne ❤")
                eb.setColor(Color.green)

                val msg = eb.build()
                e.replyEmbeds(msg).queue() }
        }
    }
}