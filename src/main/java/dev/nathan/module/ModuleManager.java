package dev.nathan.module;

import dev.nathan.Bot;
import dev.nathan.module.comp.Command;
import dev.nathan.module.comp.CommandOption;
import dev.nathan.module.impl.ModerationModule;
import dev.nathan.module.impl.UtilsModule;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

import java.util.ArrayList;

public class ModuleManager {

    @Getter
    private static final ArrayList<Module> modules = new ArrayList<>();

    @Getter
    private static final ArrayList<Command> commands = new ArrayList<>();

    public static void onInitialize() {
        modules.add(new UtilsModule());
        modules.add(new ModerationModule());

        for (Module module : ModuleManager.getModules()) {
            for (Command c : module.getCommands()) {
                System.out.println(c.getName());
                commands.add(c);
            }
        }

        for (Guild guild : Bot.INSTANCE.getGuilds()) {
            for (Command command : commands) {
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

    public static Command getCommand(String command) {
        Command cmd = null;

        for (Command c : commands) {
            if (c.getName().equalsIgnoreCase(command)) {
                cmd = c;
                break;
            }
        }

        return cmd;
    }

}
