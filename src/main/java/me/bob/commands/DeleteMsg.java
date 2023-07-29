package me.bob.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class DeleteMsg extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("clear")) return;
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
        if (event.getOptions().isEmpty()) {
            event.reply("Merci de préciser le nombre de messages à supprimer").setEphemeral(true).queue();
            return;
        }

        Integer nb = event.getOption("number").getAsInt();

        event.getChannel().getIterableHistory()
                        .takeAsync(nb)
                        .thenAccept(event.getChannel()::purgeMessages);

        event.reply(nb.toString() + " message(s) supprimé(s)").setEphemeral(true).queue();
    }
}
