package dev.nathan.commands.impl.utils;

import dev.nathan.Bot;
import dev.nathan.commands.Command;
import dev.nathan.commands.CommandCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping", "Replies with the bots ping!", CommandCategory.UTILS);
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

}
