package me.bob.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.util.ArrayList;
import java.util.List;

public class SelectRole extends ListenerAdapter {
    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponent().getId() == null) return;
        if (!event.getComponent().getId().equals("menu:role")) return;

        Member member = event.getMember();
        Guild guild = event.getGuild();

        if (member == null || guild == null) return;

        List <SelectOption> selectOptionList = event.getSelectedOptions();

        List <Role> roleList = new ArrayList<>();
        // java
        roleList.add(event.getJDA().getRoleById("717687538437062707"));
        // bedrock
        roleList.add(event.getJDA().getRoleById("717687563443503224"));

        int b = 0;
        while (b < roleList.size()) {
            guild.removeRoleFromMember(member, roleList.get(b)).queue();
            b++;
        }

        if (event.getSelectedOptions().isEmpty()) {
            event.reply("Tu n'as plus de rôle sélectionné.").setEphemeral(true).queue();
            return;
        }

        ArrayList<String> rolesSelect = new ArrayList<>();
        int i = 0;
        while (i < selectOptionList.size()) {
            SelectOption option = selectOptionList.get(i);
            switch (option.getValue()) {
                case "value:java" -> {
                    guild.addRoleToMember(member, roleList.get(0)).queue();
                    rolesSelect.add(roleList.get(0).getAsMention());
                }
                case "value:bedrock" -> {
                    guild.addRoleToMember(member, roleList.get(1)).queue();
                    rolesSelect.add(roleList.get(1).getAsMention());
                }
            }
            i++;
        }
        String string = "";
        for (String s : rolesSelect) {
            if (!string.isBlank()) { string += ", "; }
            string += s;
        }

        event.reply("Tu as désormais les rôles suivants : " + string + " tu peux les retirer à tout moment si besoin, ils ne sont utiles qu'afin de t'aider plus facilement si tu as des questions !").setEphemeral(true).queue();
    }
}
