package dev.nathan;

import dev.nathan.module.ModuleManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
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

        INSTANCE.awaitReady();

        ModuleManager.onInitialize();
        ModuleManager.getModules().forEach((module -> {
            module.upsertCommands();
            INSTANCE.addEventListener(module);
        }));

        INSTANCE.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching(INSTANCE.getGuilds().size() + " servers!"));
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
    }

}
