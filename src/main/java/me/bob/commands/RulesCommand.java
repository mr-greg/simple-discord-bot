package me.bob.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;

public class RulesCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // security
        if (!event.getName().equals("règlement")) return;
        if (!event.isFromGuild()) return;
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("Tu n'as pas la permission de faire ça :)").setEphemeral(true).queue();
            return;
        }

        // utils
        MessageChannel salon = event.getMessageChannel();

        ForumChannel serveurMinecraft = event.getGuild().getForumChannelById("1133488583458705499");
        ForumChannel aideJava = event.getGuild().getForumChannelById("1133490296735420616");
        ForumChannel aideBedrock = event.getGuild().getForumChannelById("1133491798099103764");

        // embed message
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Bienvenue sur le serveur !");
        eb.setColor(Color.WHITE);
        eb.addField("Règlement :", "<:dot:808839441229021214> Soyez pas débile, faites preuve de bon sens\n", false);
        eb.setThumbnail("https://i.imgur.com/uPGeLNp.png");

        eb.addField("Aide tutoriel Minecraft", "<:dot:808839441229021214> Aide Minecraft JAVA : " + aideJava.getAsMention() + "\n" +
                "<:dot:808839441229021214> Aide Minecraft BEDROCK : " + aideBedrock.getAsMention(), false);

        // select menu
        StringSelectMenu menu = StringSelectMenu.create("menu:role")
                .setPlaceholder("Sélectionnez votre version de Minecraft (facultatif)")
                .setRequiredRange(0,2)
                .addOption("Java", "value:java", "Je joue sur Java", Emoji.fromCustom("java", 1134956903496167544L, false))
                .addOption("Bedrock", "value:bedrock", "Je joue sur Bedrock", Emoji.fromCustom("minecraft_bedrock", 1134956222999699475L, false))
                        .build();



        // actions
        salon.sendMessageEmbeds(eb.build()).addActionRow(menu).queue();
    }
}
