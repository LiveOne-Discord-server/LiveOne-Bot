package com.liveOne.github.botDS.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CMDUsers extends ListenerAdapter {

    private final EmbedBuilder eb = new EmbedBuilder();

    // TODO: Зробить потім так щоб надсилалось повідомлення лише хто написав команду.
    @Override public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent ev) {
        switch (ev.getName()) {
            case "about": {

                eb.addField("Про LiveOne",
                            "Цей **Discord** сервер був створенний для **ютубера [Baner](https://www.youtube.com/@BansaFanchik)**", false);

                eb.addField("Про бота",
                            """
                                    **Привіт!** Я був створенний для Discord серверу **LiveOne**
                                    На данний час я знаходжусь ще на стадії [**BETA(Github REPO)**](https://www.github.com/),
                                    """, false); // TODO: Видалить потім Github REPO після того як вийдемо в pre-release, та переробить текст

                eb.setFooter("Official bot for LiveOne ❤");
                eb.setColor(Color.red);

                MessageEmbed msg = eb.build();
                ev.replyEmbeds(msg).queue();
                break;}

            case "urls": {
                eb.addField("Посилання", """
                        Ютуб: [**КЛІК**](https://www.youtube.com/@BansaFanchik)
                            Підтримати спонсорством: КЛІК
                        Тікток: [**КЛІК**](https://www.tiktok.com/)
                        Щось потім ще добавить...""", false);

                eb.setFooter("Official bot for LiveOne ❤");
                eb.setColor(Color.red);
                MessageEmbed msg = eb.build();

                ev.replyEmbeds(msg).queue();
                break;}
        }
    }
}
