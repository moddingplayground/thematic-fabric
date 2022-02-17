package net.moddingplayground.thematic.impl.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

public final class ThematicRegistry {
    public static final DefaultedRegistry<Theme> THEME = register("theme", Theme.class, "rustic");
    public static final DefaultedRegistry<Decoratable> DECORATABLE = register("decoratable", Decoratable.class,"lantern");

    private ThematicRegistry() {}

    private static <T> DefaultedRegistry<T> register(String id, Class<T> clazz, String def) {
        return FabricRegistryBuilder.createDefaulted(clazz, new Identifier(Thematic.MOD_ID, id), new Identifier(Thematic.MOD_ID, def)).buildAndRegister();
    }
}
