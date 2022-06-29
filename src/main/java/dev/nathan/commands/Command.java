package dev.nathan.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Command {

    protected final String name;
    protected final String description;
    protected final ArrayList<CommandOption> options = new ArrayList<>();

    public Command(String name, String description, String category, CommandOption... commandOptions) {
        this.name = name;
        this.description = description;
        Collections.addAll(this.options, commandOptions);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<CommandOption> getOptions() {
        return options;
    }

    public void buildCommand(Guild guild) {
        CommandCreateAction command = guild.upsertCommand(this.getName(), this.getDescription());

        if (this.getOptions().size() != 0) {
            for (CommandOption option : this.getOptions()) {
                command = command.addOption(option.getOptionType(), option.getName(), option.getDescription(), option.isRequired());
            }
        }

        command.queue();
    }

    public abstract void run(SlashCommandInteractionEvent event);

}
