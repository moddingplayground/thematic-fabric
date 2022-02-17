package net.moddingplayground.thematic.api;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.impl.ThematicImpl;
import net.moddingplayground.thematic.impl.registry.ThematicRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Thematic {
    String MOD_ID   = "thematic";
    String MOD_NAME = "Thematic";
    Logger LOGGER   = LoggerFactory.getLogger(MOD_ID);

    DefaultedRegistry<Theme>       THEME_REGISTRY       = ThematicRegistry.THEME;
    DefaultedRegistry<Decoratable> DECORATABLE_REGISTRY = ThematicRegistry.DECORATABLE;

    RegistryKey<? extends Registry<Theme>>       THEME_REGISTRY_KEY       = THEME_REGISTRY.getKey();
    RegistryKey<? extends Registry<Decoratable>> DECORATABLE_REGISTRY_KEY = DECORATABLE_REGISTRY.getKey();

    static ItemGroup getItemGroup() {
        return ThematicImpl.getInstance().getItemGroup();
    }
}
