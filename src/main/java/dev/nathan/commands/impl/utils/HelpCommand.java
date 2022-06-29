package dev.nathan.commands.impl.utils;

import dev.nathan.commands.Command;
import dev.nathan.commands.CommandCategory;
import dev.nathan.commands.CommandOption;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "Displays all commands!", CommandCategory.UTILS, new CommandOption(OptionType.STRING, "command", "The command name for the command you would like info on.", false));
    }

    @Override
    public void run(SlashCommandInteractionEvent event) {

    }

}
