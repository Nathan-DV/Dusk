package dev.nathan.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Command {

    protected final String name;
    protected final String category;
    protected final String description;

    protected final ArrayList<CommandOption> options = new ArrayList<>();
    protected final ArrayList<Permission> permissions = new ArrayList<>();

    public Command(String name, String description, String category) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<CommandOption> getOptions() {
        return options;
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public void upsertCommand(Guild guild) {
        CommandCreateAction command = guild.upsertCommand(this.getName(), this.getDescription());

        if (this.getOptions().size() != 0) {
            for (CommandOption option : this.getOptions()) {
                command = command.addOption(option.getOptionType(), option.getName(), option.getDescription(), option.isRequired());
            }
        }

        command.queue();
    }

    protected void addOptions(CommandOption... options) {
        Collections.addAll(this.options, options);
    }

    protected void addPermissions(Permission... permissions) {
        Collections.addAll(this.permissions, permissions);
    }

    public abstract void run(SlashCommandInteractionEvent event);

}
