package net.moddingplayground.thematic.api;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.item.ThematicItems;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.ThemeColors;

/**
 * References to all built-in registered themes.
 */
public interface BuiltinThemes {
    Theme RUSTIC = register("rustic", new Theme(() -> ThematicItems.ANCIENT_ROPE, new ThemeColors(0xA48365, 0x8B6C52)));
    Theme SUNKEN = register("sunken", new Theme(() -> ThematicItems.OVERGROWN_ANCHOR, new ThemeColors(0x737E96, 0x575F71)));
    Theme MECHANICAL = register("mechanical", new Theme(() -> ThematicItems.OXIDIZED_COG, new ThemeColors(0x6EC59F, 0x4C9484)));

    private static Theme register(String id, Theme theme) {
        return Registry.register(ThematicRegistry.THEME, new Identifier(Thematic.MOD_ID, id), theme);
    }
}
