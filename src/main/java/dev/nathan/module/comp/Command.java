package dev.nathan.module.comp;

import dev.nathan.module.comp.CommandOption;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.Collections;

@RequiredArgsConstructor
public abstract class Command {

    protected final String name;
    @Getter
    protected final String description;
    @Getter
    protected final String category;

    @Getter
    protected final ArrayList<CommandOption> options = new ArrayList<>();
    @Getter
    protected final ArrayList<Permission> permissions = new ArrayList<>();

    public String getDisplayName() {
        return name;
    }

    public String getName() {
        return name.toLowerCase();
    }

    protected void addOption(CommandOption option) {
        this.options.add(option);
    }

    protected void addPermissions(Permission... permissions) {
        Collections.addAll(this.permissions, permissions);
    }

    public abstract void register();
    public abstract void run(SlashCommandInteractionEvent event);

}
