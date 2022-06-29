package dev.nathan.listeners;

import dev.nathan.commands.Command;
import dev.nathan.commands.CommandManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandHandler extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (Command command : CommandManager.getCommands()) {
            if (event.getName().equals(command.getName())) {
                command.run(event);
                break;
            }
        }
    }

}
