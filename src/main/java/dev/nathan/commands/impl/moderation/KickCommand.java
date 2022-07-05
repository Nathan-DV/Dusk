package dev.nathan.commands.impl.moderation;

import dev.nathan.commands.Command;
import dev.nathan.commands.CommandCategory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class KickCommand extends Command {

    public KickCommand() {
        super("Kick", "Kick a member from the server!", CommandCategory.MODERATION);
    }

    @Override
    public void run(SlashCommandInteractionEvent event) {

    }

}
