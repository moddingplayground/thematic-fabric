package net.moddingplayground.thematic.api.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

public interface ThematicRegistry {
    DefaultedRegistry<Theme> THEME = register("theme", Theme.class, "rustic");
    DefaultedRegistry<Decoratable> DECORATABLE = register("decoratable", Decoratable.class,"lantern");

    private static <T> DefaultedRegistry<T> register(String id, Class<T> clazz, String def) {
        return FabricRegistryBuilder.createDefaulted(clazz, new Identifier(Thematic.MOD_ID, id), new Identifier(Thematic.MOD_ID, def)).buildAndRegister();
    }
}
