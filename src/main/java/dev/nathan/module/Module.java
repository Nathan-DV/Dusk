package dev.nathan.module;

import dev.nathan.module.comp.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

@RequiredArgsConstructor
public class Module extends ListenerAdapter {

    @Getter
    private final Type type;

    @Getter
    private final ArrayList<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (this.type == Type.COMMANDS) {
            for (Command command : commands) {
                if (event.getName().equals(command.getName())) {
                    if (!event.getMember().hasPermission(command.getPermissions())) {
                        MessageEmbed messageEmbed = new EmbedBuilder()
                                .setTitle(":x: You do not have the following permissions:")
                                .setDescription(this.getPermissionString(command))
                                .setColor(new Color(255, 63, 63))
                                .build();

                        event.getInteraction().replyEmbeds(messageEmbed).setEphemeral(true).queue();
                        return;
                    }

                    command.run(event);
                }
            }
        }
    }

    public String getPermissionString(Command command) {
        StringBuilder string = new StringBuilder();

        for (Permission permission : command.getPermissions()) {
            string.append("**`").append(permission.getName()).append("`** ");
        }

        return string.toString();
    }

    protected enum Type {
        COMMANDS, EVENT;
    }

}
