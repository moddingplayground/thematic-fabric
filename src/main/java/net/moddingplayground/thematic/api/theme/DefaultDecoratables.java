package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.moddingplayground.thematic.api.registry.DecoratablesRegistry;
import net.moddingplayground.thematic.block.ThematicLadderBlock;

public class DefaultDecoratables {
    public static final Decoratable LANTERN = register("%s_lantern", t -> new LanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN)));
    public static final Decoratable LADDER = register("%s_ladder", t -> new ThematicLadderBlock(FabricBlockSettings.copyOf(Blocks.LADDER)));

    private static Decoratable register(String format, Decoratable.BlockFactory blockFactory, Decoratable.ItemFactory itemFactory, Decoratable.PostRegister postRegister) {
        return register(new Decoratable(format, blockFactory, itemFactory, postRegister));
    }

    private static Decoratable register(String format, Decoratable.BlockFactory blockFactory, Decoratable.ItemFactory itemFactory) {
        return register(format, blockFactory, itemFactory, Decoratable.PostRegister.NONE);
    }

    private static Decoratable register(String format, Decoratable.BlockFactory blockFactory, Decoratable.PostRegister postRegister) {
        return register(new Decoratable(format, blockFactory, postRegister));
    }

    private static Decoratable register(String format, Decoratable.BlockFactory blockFactory) {
        return register(format, blockFactory, Decoratable.PostRegister.NONE);
    }

    private static Decoratable register(String format, AbstractBlock.Settings settings) {
        return register(new Decoratable(format, settings));
    }

    private static Decoratable register(Decoratable decoratable) {
        return DecoratablesRegistry.INSTANCE.register(decoratable);
    }
}
