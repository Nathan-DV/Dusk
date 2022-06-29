package dev.nathan.commands;

import dev.nathan.commands.impl.utils.HelpCommand;
import dev.nathan.commands.impl.utils.PingCommand;

import java.util.ArrayList;

public class CommandManager {

    private static final ArrayList<Command> commands = new ArrayList<>();

    public static void init() {
        CommandManager.getCommands().add(new PingCommand());
        CommandManager.getCommands().add(new HelpCommand());
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }
}
