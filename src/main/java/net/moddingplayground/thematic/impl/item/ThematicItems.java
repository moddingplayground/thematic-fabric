package net.moddingplayground.thematic.impl.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.item.ThemeItem;
import net.moddingplayground.thematic.api.theme.Theme;

public class ThematicItems {
    public static final Item ANCIENT_ROPE = theme("ancient_rope", BuiltinThemes.RUSTIC);
    public static final Item OVERGROWN_ANCHOR = theme("overgrown_anchor", BuiltinThemes.SUNKEN);
    public static final Item OXIDIZED_COG = theme("oxidized_cog", BuiltinThemes.MECHANICAL);

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Thematic.MOD_ID, id), item);
    }

    private static Item unstackable(String id, ItemFactory<Item> factory) {
        return register(id, factory.create(new FabricItemSettings().maxCount(1)));
    }

    private static Item theme(String id, Theme theme) {
        return unstackable(id, s -> new ThemeItem(theme, s));
    }

    @FunctionalInterface private interface ItemFactory<T extends Item> { T create(FabricItemSettings settings); }
}
