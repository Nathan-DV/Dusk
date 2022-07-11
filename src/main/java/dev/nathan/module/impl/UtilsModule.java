package dev.nathan.module.impl;

import dev.nathan.Bot;
import dev.nathan.commands.CommandManager;
import dev.nathan.module.Module;
import dev.nathan.module.comp.Command;
import dev.nathan.module.comp.CommandCategory;
import dev.nathan.module.comp.CommandOption;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class UtilsModule extends Module {

    public UtilsModule() {
        super(Type.COMMANDS);

        this.addCommand(new Command("help", "Displays all commands!", CommandCategory.UTILS) {

            @Override
            public void register() {
                this.addOption(new CommandOption(OptionType.STRING, "command", "The command name for the command you would like info on.", false));
            }

            @Override
            public void run(SlashCommandInteractionEvent event) {
                if (event.getOption("command") != null) {
                    dev.nathan.commands.Command command = CommandManager.getCommand(event.getOption("command").getAsString());

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
                        .addField(dev.nathan.commands.CommandCategory.MODERATION, getCommandsByCategory(dev.nathan.commands.CommandCategory.MODERATION), false)
                        .addField(dev.nathan.commands.CommandCategory.UTILS, getCommandsByCategory(dev.nathan.commands.CommandCategory.UTILS), false)
                        .build();

                event.getInteraction().replyEmbeds(messageEmbed).queue();
            }

        });

        this.addCommand(new Command("ping", "Pong!", CommandCategory.UTILS) {

            @Override
            public void register() {

            }

            @Override
            public void run(SlashCommandInteractionEvent event) {
                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setTitle(":ping_pong: Pong!")
                        .setColor(Bot.THEME)
                        .setDescription("**Ping: `" + Bot.INSTANCE.getGatewayPing() + " MS`**")
                        .build();

                event.getInteraction().replyEmbeds(messageEmbed).queue();
            }

        });
    }

    public String getCommandsByCategory(String category) {
        AtomicReference<String> string = new AtomicReference<>("");
        Stream<dev.nathan.commands.Command> commands = CommandManager.getCommands().stream().filter((command) -> command.getCategory().equals(category));

        commands.forEach(command -> {
            String value = string.get();
            string.set(value + "**`" + command.getName() + "`** ");
        });

        return string.get();
    }

}
