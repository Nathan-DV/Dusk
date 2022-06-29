package dev.nathan.commands.impl;

import dev.nathan.Bot;
import dev.nathan.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping", "Replies with the bots ping!");
    }

    @Override
    public void run(SlashCommandInteractionEvent event) {
        MessageEmbed messageEmbed = new EmbedBuilder()
                .setTitle(":ping_pong: Pong!")
                .setDescription("**Ping: `" + Bot.INSTANCE.getGatewayPing() + " MS`**")
                .build();

        event.getInteraction().replyEmbeds(messageEmbed);
    }

}
