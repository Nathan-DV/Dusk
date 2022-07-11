package dev.nathan.module.comp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@RequiredArgsConstructor
public class CommandOption {

    @Getter
    private final OptionType optionType;

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final boolean required;

}
