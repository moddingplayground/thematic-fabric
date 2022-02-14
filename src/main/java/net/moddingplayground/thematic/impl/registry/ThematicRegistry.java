package net.moddingplayground.thematic.impl.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

public final class ThematicRegistry {
    public static final SimpleRegistry<Theme> THEME = FabricRegistryBuilder.createSimple(Theme.class, new Identifier(Thematic.MOD_ID, "theme"))
                                                                           .buildAndRegister();

    public static final SimpleRegistry<Decoratable> DECORATABLE = FabricRegistryBuilder.createSimple(Decoratable.class, new Identifier(Thematic.MOD_ID, "decoratable"))
                                                                                       .buildAndRegister();

    private ThematicRegistry() {}
}
