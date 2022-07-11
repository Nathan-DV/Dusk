package dev.nathan.module;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;

public class ModuleManager {

    @Getter
    private static final ArrayList<Module> modules = new ArrayList<>();

    public static void onInitialize() {
        final Set<Class<? extends Module>> modules = new Reflections("dev.digitaldragon.module.impl").getSubTypesOf(Module.class);
        modules.addAll(modules);
    }

}
