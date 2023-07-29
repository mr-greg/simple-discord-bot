package me.bob;

import io.github.cdimascio.dotenv.Dotenv;
import me.bob.commands.DeleteMsg;
import me.bob.commands.RulesCommand;
import me.bob.events.JoinEvent;
import me.bob.events.SelectRole;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class Bob {
    public static void main(String[] args) throws InterruptedException {
        Dotenv dotenv = Dotenv.load();

        JDA bot = JDABuilder.createDefault(dotenv.get("TOKEN"))
                .setActivity(Activity.streaming("Venez les belles chèvres", "https://www.twitch.tv/zaacklachevre"))

                .addEventListeners(new JoinEvent())
                .addEventListeners(new RulesCommand())
                .addEventListeners(new SelectRole())
                .addEventListeners(new DeleteMsg())

                .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .build().awaitReady();

        Guild guild = bot.getGuildById("712897369561235496");
        if (guild != null) {
            guild.updateCommands().addCommands(
                    Commands.slash("règlement", "pop embed règlement"),
                    Commands.slash("clear", "option = nombre de messages à clear")
                            .addOption(OptionType.INTEGER, "number", "nb msg à delete")
            ).queue();
        }
    }
}
