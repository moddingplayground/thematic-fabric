package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
import net.moddingplayground.frame.api.registry.StateRegistry;
import net.moddingplayground.thematic.api.registry.DecoratablesRegistry;
import net.moddingplayground.thematic.block.ThematicBlock;
import net.moddingplayground.thematic.block.ThematicLadderBlock;
import net.moddingplayground.thematic.block.themed.MechanicalLanternBlock;
import net.moddingplayground.thematic.block.themed.RusticLanternBlock;
import net.moddingplayground.thematic.block.themed.SunkenLanternBlock;

import static net.moddingplayground.thematic.api.theme.Theme.*;

// TODO lessen hardcoding
public class DefaultDecoratables {
    public static final Decoratable LANTERN = register("%s_lantern", t -> {
        if (t == RUSTIC) return new RusticLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN));
        if (t == SUNKEN) return new SunkenLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN));
        if (t == MECHANICAL) return new MechanicalLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN));
        return new LanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN));
    });

    public static final Decoratable LADDER = register("%s_ladder", t -> {
        if (t == SUNKEN) return new ThematicLadderBlock(
            FabricBlockSettings.copyOf(Blocks.LADDER)
                               .sounds(BlockSoundGroup.CHAIN)
                               .strength(3.5f).requiresTool()
        );
        if (t == MECHANICAL) return new ThematicLadderBlock(
            FabricBlockSettings.copyOf(Blocks.LADDER)
                               .sounds(BlockSoundGroup.COPPER)
                               .strength(3.5f).requiresTool()
        );
        return new ThematicLadderBlock(FabricBlockSettings.copyOf(Blocks.LADDER));
    }, (theme, decoratable, block) -> {
        if (theme == RUSTIC) {
            FuelRegistry fuel = FuelRegistry.INSTANCE;
            fuel.add(block, 300);
        }

        StateRegistry.LADDERS.add(block);
        StateRegistry.LADDERS_DEATH_MESSAGES.add(block);
    });

    public static final Decoratable BOOKSHELF = register("%s_bookshelf", t -> {
        if (t == RUSTIC) return new ThematicBlock(
            FabricBlockSettings.copyOf(Blocks.BOOKSHELF)
                               .mapColor(MapColor.SPRUCE_BROWN)
        );
        if (t == SUNKEN) return new ThematicBlock(
            FabricBlockSettings.copyOf(Blocks.BOOKSHELF)
                               .sounds(BlockSoundGroup.NETHERITE)
                               .strength(3.5f).requiresTool()
        );
        if (t == MECHANICAL) return new ThematicBlock(
            FabricBlockSettings.copyOf(Blocks.BOOKSHELF)
                               .sounds(BlockSoundGroup.COPPER)
                               .strength(3.5f).requiresTool()
        );
        return new ThematicBlock(FabricBlockSettings.copyOf(Blocks.BOOKSHELF));
    }, (theme, decoratable, block) -> {
        if (theme == RUSTIC) {
            FuelRegistry fuel = FuelRegistry.INSTANCE;
            fuel.add(block, 300);
            FlammableBlockRegistry flammable = FlammableBlockRegistry.getDefaultInstance();
            flammable.add(block, 30, 20);
        }

        StateRegistry.BOOKSHELVES.add(block);
    });

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
