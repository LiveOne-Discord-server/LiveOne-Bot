package com.liveOne.github.botDS;

import com.liveOne.github.botDS.commands.CMDUsers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import static com.liveOne.github.botDS.configuration.MConfig.config;
import static com.liveOne.github.botDS.configuration.MConfig.saveConfiguration;

public class Main {
    public static void main(String[] args) {
        saveConfiguration();
        load();
    }

    static void load() {
        JDA bot = JDABuilder.createLight(config.getString("Token")).addEventListeners(new CMDUsers())
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.watching(config.getString("Activity-Text")))
                .build();

        bot.updateCommands().addCommands(
                Commands.slash("about", "about discord server and bot").setGuildOnly(true),
                Commands.slash("urls", "urls").setGuildOnly(true)
        ).queue();

        try {bot.awaitReady();} catch (InterruptedException e) {throw new RuntimeException(e);}
    }
}