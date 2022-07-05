package dev.nathan.commands.impl.utils;

import dev.nathan.Bot;
import dev.nathan.commands.Command;
import dev.nathan.commands.CommandCategory;
import dev.nathan.commands.CommandManager;
import dev.nathan.commands.CommandOption;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("Help", "Displays all commands!", CommandCategory.UTILS);
        this.addOptions(new CommandOption(OptionType.STRING, "command", "The command name for the command you would like info on.", false));
    }

    @Override
    public void run(SlashCommandInteractionEvent event) {
        if (event.getOption("command") != null) {
            Command command = CommandManager.getCommand(event.getOption("command").getAsString());

            if (command == null) {
                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setColor(Bot.THEME)
                        .setDescription("I could not find the command you provided!")
                        .build();

                event.getInteraction().replyEmbeds(messageEmbed).setEphemeral(true).queue();

                return;
            }

            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setAuthor(command.getDisplayName() + " Command | " + event.getGuild().getName(), event.getGuild().getIconUrl())
                    .setColor(Bot.THEME)
                    .setThumbnail(Bot.INSTANCE.getSelfUser().getAvatarUrl())
                    .addField("Name", "**`" + command.getDisplayName() + "`**", true)
                    .addField("Description", "**`" + command.getDescription() + "`**", true)
                    .addField("Category", "**`" + command.getCategory() + "`**", false)
                    .build();

            event.getInteraction().replyEmbeds(messageEmbed).queue();

            return;
        }

        MessageEmbed messageEmbed = new EmbedBuilder()
                .setAuthor("Help Command | " + event.getGuild().getName(), event.getGuild().getIconUrl())
                .setColor(Bot.THEME)
                .setThumbnail(Bot.INSTANCE.getSelfUser().getAvatarUrl())
                .addField(CommandCategory.MODERATION, getCommandsByCategory(CommandCategory.MODERATION), false)
                .addField(CommandCategory.UTILS, getCommandsByCategory(CommandCategory.UTILS), false)
                .build();

        event.getInteraction().replyEmbeds(messageEmbed).queue();
    }

    public String getCommandsByCategory(String category) {
        AtomicReference<String> string = new AtomicReference<>("");
        Stream<Command> commands = CommandManager.getCommands().stream().filter((command) -> command.getCategory().equals(category));

        commands.forEach(command -> {
            String value = string.get();
            string.set(value + "**`" + command.getName() + "`** ");
        });

        return string.get();
    }

}
