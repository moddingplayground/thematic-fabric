package net.moddingplayground.thematic.impl.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

public final class ThematicRegistry {
    public static final SimpleRegistry<Theme> THEME = register("theme", Theme.class);
    public static final SimpleRegistry<Decoratable> DECORATABLE = register("decoratable", Decoratable.class);

    private ThematicRegistry() {}

    private static <T> SimpleRegistry<T> register(String id, Class<T> clazz) {
        return FabricRegistryBuilder.createSimple(clazz, new Identifier(Thematic.MOD_ID, id)).buildAndRegister();
    }
}
