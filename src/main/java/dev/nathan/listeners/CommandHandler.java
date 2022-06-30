package dev.nathan.listeners;

import dev.nathan.Bot;
import dev.nathan.commands.Command;
import dev.nathan.commands.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandHandler extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (Command command : CommandManager.getCommands()) {
            if (event.getName().equals(command.getName())) {
                if (!Objects.requireNonNull(event.getMember()).hasPermission(command.getPermissions())) {
                    MessageEmbed messageEmbed = new EmbedBuilder()
                            .setTitle(":x: You do not have the following permissions:")
                            .setDescription(this.getPermissionString(command))
                            .setColor(Bot.THEME)
                            .build();

                    event.getInteraction().replyEmbeds(messageEmbed).setEphemeral(true).queue();
                    break;
                } else {
                    command.run(event);
                    break;
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

}
