package dev.nathan;

import dev.nathan.listeners.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {

    public static JDA INSTANCE;

    public static void main(String[] args) throws LoginException, InterruptedException {
        INSTANCE = JDABuilder.createDefault("")
                .setDisabledIntents(GatewayIntent.GUILD_WEBHOOKS)
                .enableCache(CacheFlag.VOICE_STATE)
                .setActivity(Activity.watching("all servers!"))
                .addEventListeners(new CommandHandler())
                .build();

        INSTANCE.awaitReady();
    }

}
