package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.block.themed.MechanicalLanternBlock;
import net.moddingplayground.thematic.block.themed.RusticLanternBlock;
import net.moddingplayground.thematic.block.themed.SunkenLanternBlock;
import net.moddingplayground.thematic.block.ThematicLadderBlock;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

public enum Theme {
    RUSTIC(Map.of(
        LANTERN, d -> new Decoratable(d, t -> new RusticLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN))),
        LADDER, d -> new Decoratable(d, (theme, decoratable, block) -> {
            FuelRegistry fuel = FuelRegistry.INSTANCE;
            fuel.add(block, 300);
        })
    ), false),
    SUNKEN(Map.of(
        LANTERN, d -> new Decoratable(d, t -> new SunkenLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN))),
        LADDER, d -> new Decoratable(d, t -> new ThematicLadderBlock(FabricBlockSettings.copyOf(Blocks.LADDER).strength(3.5f).requiresTool()))
    ), true),
    MECHANICAL(Map.of(
        LANTERN, d -> new Decoratable(d, t -> new MechanicalLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN))),
        LADDER, d -> new Decoratable(d, t -> new ThematicLadderBlock(FabricBlockSettings.copyOf(Blocks.LADDER).strength(3.5f).requiresTool()))
    ), true);

    private static final Theme[] THEMES = Theme.values();

    private final String id;
    private final Map<Decoratable, Function<Decoratable, Decoratable>> overrides;
    private final boolean metallic;

    Theme(Map<Decoratable, Function<Decoratable, Decoratable>> overrides, boolean metallic) {
        this.id = this.name().toLowerCase();
        this.overrides = overrides;
        this.metallic = metallic;
    }

    public String getId() {
        return this.id;
    }

    public Decoratable override(Decoratable decoratable) {
        return Optional.ofNullable(this.overrides.get(decoratable)).orElse(d -> decoratable).apply(decoratable);
    }

    public boolean isMetallic() {
        return this.metallic;
    }

    public Block get(Decoratable decoratable) {
        return Registry.BLOCK.get(Thematic.defaultedId(decoratable.format(this)));
    }

    public static void forEach(Consumer<Theme> action) {
        for (Theme theme : THEMES) action.accept(theme);
    }
}
