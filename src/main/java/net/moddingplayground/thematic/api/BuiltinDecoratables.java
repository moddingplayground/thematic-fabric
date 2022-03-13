package net.moddingplayground.thematic.api;

import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.data.preset.bannerpattern.BannerPatternWithItemDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.BookshelfDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.GateDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.LadderDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.LadderVaryingDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.LanternDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.SeatDecoratableData;
import net.moddingplayground.thematic.impl.block.theme.lantern.MechanicalLanternBlock;
import net.moddingplayground.thematic.impl.block.theme.lantern.RusticLanternBlock;
import net.moddingplayground.thematic.impl.block.theme.lantern.SunkenLanternBlock;
import net.moddingplayground.thematic.impl.theme.data.block.entity.chest.MetalChestDecoratableData;
import net.moddingplayground.thematic.impl.theme.data.block.entity.chest.RusticChestDecoratableData;
import net.moddingplayground.thematic.impl.theme.data.block.entity.chest.TrappedMetalChestDecoratableData;
import net.moddingplayground.thematic.impl.theme.data.block.entity.chest.TrappedRusticChestDecoratableData;

import static net.moddingplayground.thematic.api.BuiltinThemes.*;

/**
 * References to all built-in registered decoratables.
 */
public interface BuiltinDecoratables {
    Decoratable LADDER = register("ladder", "%s_ladder")
        .add(RUSTIC, LadderDecoratableData::new)
        .add(SUNKEN, LadderVaryingDecoratableData.createMetal(3, s -> s.sounds(BlockSoundGroup.CHAIN).strength(3.5f).requiresTool()))
        .add(MECHANICAL, LadderDecoratableData.createMetal(s -> s.sounds(BlockSoundGroup.COPPER).strength(3.5f).requiresTool()));

    Decoratable GATE = register("gate", "%s_gate")
        .add(RUSTIC, GateDecoratableData.create(Blocks.SPRUCE_PLANKS))
        .add(SUNKEN, GateDecoratableData.createMetal(Blocks.IRON_BARS, s -> s.sounds(BlockSoundGroup.NETHERITE).requiresTool()))
        .add(MECHANICAL, GateDecoratableData.createMetal(Blocks.OXIDIZED_COPPER, s -> s.sounds(BlockSoundGroup.COPPER).requiresTool()));

    Decoratable LANTERN = register("lantern", "%s_lantern")
        .add(RUSTIC, LanternDecoratableData.create(RusticLanternBlock::new))
        .add(SUNKEN, LanternDecoratableData.create(SunkenLanternBlock::new))
        .add(MECHANICAL, LanternDecoratableData.create(MechanicalLanternBlock::new));

    Decoratable BOOKSHELF = register("bookshelf", "%s_bookshelf")
        .add(RUSTIC, BookshelfDecoratableData.create(s -> s.mapColor(MapColor.SPRUCE_BROWN)))
        .add(SUNKEN, BookshelfDecoratableData.createMetal(s -> s.sounds(BlockSoundGroup.NETHERITE).strength(3.5f).requiresTool()))
        .add(MECHANICAL, BookshelfDecoratableData.createMetal(s -> s.sounds(BlockSoundGroup.COPPER).strength(3.5f).requiresTool()));

    Decoratable CHEST = register("chest", "%s_chest")
        .add(RUSTIC, RusticChestDecoratableData.create(Blocks.SPRUCE_PLANKS))
        .add(SUNKEN, MetalChestDecoratableData.create(Blocks.IRON_BARS, s -> s.sounds(BlockSoundGroup.NETHERITE).requiresTool()))
        .add(MECHANICAL, MetalChestDecoratableData.create(Blocks.OXIDIZED_COPPER, s -> s.sounds(BlockSoundGroup.COPPER).requiresTool()));

    Decoratable TRAPPED_CHEST = register("trapped_chest", "trapped_%s_chest")
        .add(RUSTIC, TrappedRusticChestDecoratableData.create(Blocks.SPRUCE_PLANKS))
        .add(SUNKEN, TrappedMetalChestDecoratableData.create(Blocks.IRON_BARS, s -> s.sounds(BlockSoundGroup.NETHERITE).requiresTool()))
        .add(MECHANICAL, TrappedMetalChestDecoratableData.create(Blocks.OXIDIZED_COPPER, s -> s.sounds(BlockSoundGroup.COPPER).requiresTool()));

    Decoratable SEAT = register("seat", "%s_seat")
        .add(RUSTIC, SeatDecoratableData.create(Blocks.SPRUCE_PLANKS))
        .add(SUNKEN, SeatDecoratableData.createMetal(Blocks.IRON_BARS, s -> s.sounds(BlockSoundGroup.NETHERITE).requiresTool()))
        .add(MECHANICAL, SeatDecoratableData.createMetal(Blocks.OXIDIZED_COPPER, s -> s.sounds(BlockSoundGroup.COPPER).requiresTool()));

    Decoratable BANNER_PATTERN = register("banner_pattern")
        .add(RUSTIC, BannerPatternWithItemDecoratableData.create("rope_banner_pattern"))
        .add(SUNKEN, BannerPatternWithItemDecoratableData.create("anchor_banner_pattern"))
        .add(MECHANICAL, BannerPatternWithItemDecoratableData.create("cog_banner_pattern"));

    private static Decoratable register(String id, Decoratable decoratable) {
        return Registry.register(ThematicRegistry.DECORATABLE, new Identifier(Thematic.MOD_ID, id), decoratable);
    }

    private static Decoratable register(String id, String format) {
        return register(id, new Decoratable(format));
    }

    private static Decoratable register(String id) {
        return register(id, new Decoratable());
    }
}
