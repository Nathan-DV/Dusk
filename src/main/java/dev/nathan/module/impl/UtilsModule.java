package dev.nathan.module.impl;

import dev.nathan.Bot;
import dev.nathan.module.Module;
import dev.nathan.module.ModuleManager;
import dev.nathan.module.comp.Command;
import dev.nathan.module.comp.CommandCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class UtilsModule extends Module {

    public UtilsModule() {
        super(Type.COMMANDS);

        this.addCommand(new Command("Help", "Displays all commands!", CommandCategory.UTILS) {

            @Override
            public void register() {

            }

            @Override
            public void run(SlashCommandInteractionEvent event) {
                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setAuthor("Help Command | " + event.getGuild().getName(), event.getGuild().getIconUrl())
                        .setColor(Bot.THEME)
                        .setThumbnail(Bot.INSTANCE.getSelfUser().getAvatarUrl())
                        .addField(CommandCategory.MODERATION, getCommandsByCategory(CommandCategory.MODERATION), false)
                        .addField(CommandCategory.UTILS, getCommandsByCategory(CommandCategory.UTILS), false)
                        .build();

                event.getInteraction().replyEmbeds(messageEmbed).queue();
            }

        });

        this.addCommand(new Command("Ping", "Pong!", CommandCategory.UTILS) {

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
        Stream<Command> commands = ModuleManager.getCommands().stream().filter((command) -> command.getCategory().equals(category));

        commands.forEach(command -> {
            String value = string.get();
            string.set(value + "**`" + command.getName() + "`** ");
        });

        return string.get();
    }

}
