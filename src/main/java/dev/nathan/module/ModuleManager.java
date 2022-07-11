package dev.nathan.module;

import dev.nathan.module.comp.Command;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;

public class ModuleManager {

    @Getter
    private static final ArrayList<Module> modules = new ArrayList<>();

    @Getter
    private static final ArrayList<Command> commands = new ArrayList<>();

    public static void onInitialize() {
        Set<Class<? extends Module>> m = new Reflections("dev.nathan.module.impl").getSubTypesOf(Module.class);

        m.forEach((module) -> {
            try {
                modules.add(module.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        for (Module module : ModuleManager.getModules()) {
            commands.addAll(module.getCommands());
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
