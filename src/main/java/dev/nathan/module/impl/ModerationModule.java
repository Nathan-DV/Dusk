package dev.nathan.module.impl;

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

public class ModerationModule extends Module {

    public ModerationModule() {
        super(Type.COMMANDS);

        this.addCommand(new Command("kick", "Ban a member from the server!", CommandCategory.MODERATION) {

            @Override
            public void register() {
                this.addOption(new CommandOption(OptionType.MENTIONABLE, "member", "Member you would like to kick!", true));
                this.addOption(new CommandOption(OptionType.STRING, "reason", "Reason why you would like to kick the provided member!", false));
                this.addPermissions(Permission.KICK_MEMBERS);
            }

            @Override
            public void run(SlashCommandInteractionEvent event) {
                final Member member = event.getOption("member").getAsMember();
                String reason;

                try {
                    reason = event.getOption("reason").getAsString();
                } catch (NullPointerException error) {
                    reason = "N/A";
                }

                try {
                    member.kick(reason);
                } catch (Exception error) {
                    if (error instanceof  IllegalArgumentException) {
                        event.reply("There was an error handling that request, check my permissions!").setEphemeral(true).queue();
                        return;
                    } else if (error instanceof ErrorResponseException) {
                        event.reply("There was an error handling that request (dropped the ban hammer)").setEphemeral(true).queue();
                        return;
                    } else {
                        event.reply("There was an error handling that request (unknown error)").setEphemeral(true).queue();
                        return;
                    }
                }

                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setTitle("Kicked Member:")
                        .setDescription("Kicked " + member.getUser().getName())
                        .setThumbnail(member.getAvatarUrl())
                        .addField("Reason", reason, true)
                        .build();

                event.getInteraction().replyEmbeds(messageEmbed).setEphemeral(true).queue();
            }

        });

        this.addCommand(new Command("ban", "Ban a member from the server!", CommandCategory.MODERATION) {

            @Override
            public void register() {
                this.addOption(new CommandOption(OptionType.MENTIONABLE, "member", "Member you would like to ban!", true));
                this.addOption(new CommandOption(OptionType.STRING, "reason", "Reason why you would like to ban the provided member!", false));
                this.addPermissions(Permission.BAN_MEMBERS);
            }

            @Override
            public void run(SlashCommandInteractionEvent event) {
                final Member member = event.getOption("member").getAsMember();
                String reason;

                try {
                    reason = event.getOption("reason").getAsString();
                } catch (NullPointerException error) {
                    reason = "N/A";
                }

                try {
                    member.ban(0, reason);
                } catch (Exception error) {
                    if (error instanceof  IllegalArgumentException) {
                        event.reply("There was an error handling that request, check my permissions!").setEphemeral(true).queue();
                        return;
                    } else if (error instanceof ErrorResponseException) {
                        event.reply("There was an error handling that request (dropped the ban hammer)").setEphemeral(true).queue();
                        return;
                    } else {
                        event.reply("There was an error handling that request (unknown error)").setEphemeral(true).queue();
                        return;
                    }
                }

                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setTitle("Banned Member:")
                        .setDescription("Banned " + member.getUser().getName())
                        .setThumbnail(member.getAvatarUrl())
                        .addField("Reason", reason, true)
                        .build();

                event.getInteraction().replyEmbeds(messageEmbed).setEphemeral(true).queue();
            }

        });
    }

}
