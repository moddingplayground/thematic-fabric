package net.moddingplayground.thematic.impl.theme;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.ThemeColors;
import net.moddingplayground.thematic.impl.item.ThematicItems;

public final class BuiltinThemesImpl {
    public static final Theme RUSTIC = register("rustic", new Theme(() -> ThematicItems.ANCIENT_ROPE, new ThemeColors(0xA48365, 0x8B6C52)));
    public static final Theme SUNKEN = register("sunken", new Theme(() -> ThematicItems.OVERGROWN_ANCHOR, new ThemeColors(0x737E96, 0x575F71)));
    public static final Theme MECHANICAL = register("mechanical", new Theme(() -> ThematicItems.OXIDIZED_COG, new ThemeColors(0x6EC59F, 0x4C9484)));

    private BuiltinThemesImpl() {}

    private static Theme register(String id, Theme theme) {
        return Registry.register(Thematic.THEME_REGISTRY, new Identifier(Thematic.MOD_ID, id), theme);
    }
}
