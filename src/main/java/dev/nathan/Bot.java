package dev.nathan;

import dev.nathan.module.Module;
import dev.nathan.module.ModuleManager;
import dev.nathan.module.comp.Command;
import dev.nathan.module.comp.CommandOption;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;

public class Bot {

    public static JDA INSTANCE;
    public static final Color THEME = new Color(55, 200, 255);

    private Bot() throws LoginException, InterruptedException {

        INSTANCE = JDABuilder.createDefault(Config.BOT_TOKEN)
                .setDisabledIntents(GatewayIntent.GUILD_WEBHOOKS)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();

        ModuleManager.onInitialize();

        for (Module module : ModuleManager.getModules()) {
            INSTANCE.addEventListener(module);
        }

        INSTANCE.awaitReady();
        INSTANCE.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching(INSTANCE.getGuilds().size() + " servers!"));

        for (Guild guild : Bot.INSTANCE.getGuilds()) {
            for (Command command : ModuleManager.getCommands()) {
                command.register();
                CommandCreateAction cmd = guild.upsertCommand(command.getName(), command.getDescription());

                if (command.getOptions().size() != 0) {
                    for (CommandOption option : command.getOptions()) {
                        cmd = cmd.addOption(option.getOptionType(), option.getName(), option.getDescription(), option.isRequired());
                    }
                }

                cmd.queue();
            }
        }
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
    }

}
