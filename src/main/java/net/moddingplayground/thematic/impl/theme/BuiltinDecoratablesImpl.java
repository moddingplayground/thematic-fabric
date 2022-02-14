package net.moddingplayground.thematic.impl.theme;

import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.impl.block.theme.MechanicalLanternBlock;
import net.moddingplayground.thematic.impl.block.theme.RusticLanternBlock;
import net.moddingplayground.thematic.impl.block.theme.SunkenLanternBlock;
import net.moddingplayground.thematic.impl.theme.data.BookshelfThemeData;
import net.moddingplayground.thematic.impl.theme.data.LadderThemeData;
import net.moddingplayground.thematic.impl.theme.data.LanternThemeData;
import net.moddingplayground.thematic.impl.theme.data.SunkenLadderThemeData;

import static net.moddingplayground.thematic.impl.theme.BuiltinThemesImpl.*;

public final class BuiltinDecoratablesImpl {
    public static final Decoratable LANTERN = register("lantern", "%s_lantern")
        .add(RUSTIC, t -> LanternThemeData.of(t, RusticLanternBlock::new))
        .add(SUNKEN, t -> LanternThemeData.of(t, SunkenLanternBlock::new))
        .add(MECHANICAL, t -> LanternThemeData.of(t, MechanicalLanternBlock::new));

    public static final Decoratable LADDER = register("ladder", "%s_ladder")
        .add(RUSTIC, LadderThemeData::of)
        .add(SUNKEN, t -> SunkenLadderThemeData.of(t, s -> s.sounds(BlockSoundGroup.CHAIN).strength(3.5f).requiresTool(), false))
        .add(MECHANICAL, t -> LadderThemeData.of(t, s -> s.sounds(BlockSoundGroup.COPPER).strength(3.5f).requiresTool(), false));

    public static final Decoratable BOOKSHELF = register("bookshelf", "%s_bookshelf")
        .add(RUSTIC, t -> BookshelfThemeData.of(t, s -> s.mapColor(MapColor.SPRUCE_BROWN), true))
        .add(SUNKEN, t -> BookshelfThemeData.of(t, s -> s.sounds(BlockSoundGroup.NETHERITE).strength(3.5f).requiresTool()))
        .add(MECHANICAL, t -> BookshelfThemeData.of(t, s -> s.sounds(BlockSoundGroup.COPPER).strength(3.5f).requiresTool()));

    private BuiltinDecoratablesImpl() {}

    private static Decoratable register(String id, String format) {
        return Registry.register(Thematic.DECORATABLE_REGISTRY, new Identifier(Thematic.MOD_ID, id), new Decoratable(format));
    }
}
