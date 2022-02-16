package net.moddingplayground.thematic.impl.theme;

import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.data.preset.bannerpattern.BannerPatternWithItemDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.BookshelfDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.LadderDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.LadderVaryingDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.LanternDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.ChestDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.TrappedChestDecoratableData;
import net.moddingplayground.thematic.impl.block.theme.lantern.MechanicalLanternBlock;
import net.moddingplayground.thematic.impl.block.theme.lantern.RusticLanternBlock;
import net.moddingplayground.thematic.impl.block.theme.lantern.SunkenLanternBlock;

import static net.moddingplayground.thematic.impl.theme.BuiltinThemesImpl.*;

public final class BuiltinDecoratablesImpl {
    public static final Decoratable BANNER_PATTERN = register("banner_pattern", "%s")
        .add(RUSTIC, t -> new BannerPatternWithItemDecoratableData(t, "rope_banner_pattern"))
        .add(SUNKEN, t -> new BannerPatternWithItemDecoratableData(t, "anchor_banner_pattern"))
        .add(MECHANICAL, t -> new BannerPatternWithItemDecoratableData(t, "cog_banner_pattern"));

    public static final Decoratable LANTERN = register("lantern", "%s_lantern")
        .add(RUSTIC, t -> new LanternDecoratableData(t, RusticLanternBlock::new))
        .add(SUNKEN, t -> new LanternDecoratableData(t, SunkenLanternBlock::new))
        .add(MECHANICAL, t -> new LanternDecoratableData(t, MechanicalLanternBlock::new));

    public static final Decoratable LADDER = register("ladder", "%s_ladder")
        .add(RUSTIC, LadderDecoratableData::new)
        .add(SUNKEN, t -> new LadderVaryingDecoratableData(t, 3, s -> s.sounds(BlockSoundGroup.CHAIN).strength(3.5f).requiresTool(), false))
        .add(MECHANICAL, t -> new LadderDecoratableData(t, s -> s.sounds(BlockSoundGroup.COPPER).strength(3.5f).requiresTool(), false));

    public static final Decoratable BOOKSHELF = register("bookshelf", "%s_bookshelf")
        .add(RUSTIC, t -> new BookshelfDecoratableData(t, s -> s.mapColor(MapColor.SPRUCE_BROWN)))
        .add(SUNKEN, t -> new BookshelfDecoratableData(t, s -> s.sounds(BlockSoundGroup.NETHERITE).strength(3.5f).requiresTool(), false))
        .add(MECHANICAL, t -> new BookshelfDecoratableData(t, s -> s.sounds(BlockSoundGroup.COPPER).strength(3.5f).requiresTool(), false));

    public static final Decoratable CHEST = register("chest", "%s_chest")
        .add(RUSTIC, t -> new ChestDecoratableData(t, Blocks.SPRUCE_PLANKS))
        .add(SUNKEN, t -> new ChestDecoratableData(t, Blocks.CHAIN, s -> s.sounds(BlockSoundGroup.NETHERITE).requiresTool(), false))
        .add(MECHANICAL, t -> new ChestDecoratableData(t, Blocks.OXIDIZED_COPPER, s -> s.sounds(BlockSoundGroup.COPPER).requiresTool(), false));

    public static final Decoratable TRAPPED_CHEST = register("trapped_chest", "trapped_%s_chest")
        .add(RUSTIC, t -> new TrappedChestDecoratableData(t, Blocks.SPRUCE_PLANKS))
        .add(SUNKEN, t -> new TrappedChestDecoratableData(t, Blocks.CHAIN, s -> s.sounds(BlockSoundGroup.NETHERITE).requiresTool(), false))
        .add(MECHANICAL, t -> new TrappedChestDecoratableData(t, Blocks.OXIDIZED_COPPER, s -> s.sounds(BlockSoundGroup.COPPER).requiresTool(), false));

    private BuiltinDecoratablesImpl() {}

    private static Decoratable register(String id, String format) {
        return Registry.register(Thematic.DECORATABLE_REGISTRY, new Identifier(Thematic.MOD_ID, id), new Decoratable(format));
    }
}
