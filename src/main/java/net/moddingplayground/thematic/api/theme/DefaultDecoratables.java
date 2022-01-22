package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.moddingplayground.thematic.api.registry.DecoratablesRegistry;
import net.moddingplayground.thematic.block.ThemedLanternBlock;
import net.moddingplayground.thematic.block.vanilla.PublicLadderBlock;

public class DefaultDecoratables {
    public static final Decoratable LANTERN = register("%s_lantern", t -> new ThemedLanternBlock(t, FabricBlockSettings.copyOf(Blocks.LANTERN)));
    public static final Decoratable LADDER = register("%s_ladder", t -> {
        Theme.Data data = t.getData();
        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.LADDER);
        return new PublicLadderBlock(data.metallic() ? settings.strength(3.5f).requiresTool() : settings);
    });

    private static Decoratable register(String format, Decoratable.BlockFactory blockFactory, Decoratable.ItemFactory itemFactory) {
        return register(new Decoratable(format, blockFactory, itemFactory));
    }

    private static Decoratable register(String format, Decoratable.BlockFactory blockFactory) {
        return register(new Decoratable(format, blockFactory));
    }

    private static Decoratable register(String format, AbstractBlock.Settings settings) {
        return register(new Decoratable(format, settings));
    }

    private static Decoratable register(Decoratable decoratable) {
        return DecoratablesRegistry.INSTANCE.register(decoratable);
    }
}
