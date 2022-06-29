package dev.nathan.commands;

import dev.nathan.commands.impl.PingCommand;

import java.util.ArrayList;

public class CommandManager {

    private static final ArrayList<Command> commands = new ArrayList<>();

    public static void init() {
        CommandManager.getCommands().add(new PingCommand());
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }
}
