package dev.nathan.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class CommandOption {

    private final String name;
    private final boolean required;
    private final String description;
    private final OptionType optionType;

    public CommandOption(OptionType optionType, String name, String description, boolean required) {
        this.name = name;
        this.required = required;
        this.optionType = optionType;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public boolean isRequired() {
        return required;
    }

}
