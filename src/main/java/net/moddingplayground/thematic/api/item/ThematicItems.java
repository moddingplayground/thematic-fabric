package net.moddingplayground.thematic.api.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.loottables.v0.LootTableAdditions;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.stream.Stream;

public interface ThematicItems {
    Item ANCIENT_ROPE = theme("ancient_rope", BuiltinThemes.RUSTIC);
    Item OVERGROWN_ANCHOR = theme("overgrown_anchor", BuiltinThemes.SUNKEN);
    Item OXIDIZED_COG = theme("oxidized_cog", BuiltinThemes.MECHANICAL);

    static void onInitialize() {
        loot(BuiltinThemes.RUSTIC, LootTables.ABANDONED_MINESHAFT_CHEST, LootTables.PILLAGER_OUTPOST_CHEST, LootTables.WOODLAND_MANSION_CHEST);
        loot(BuiltinThemes.SUNKEN, LootTables.SHIPWRECK_TREASURE_CHEST, LootTables.BURIED_TREASURE_CHEST, LootTables.UNDERWATER_RUIN_SMALL_CHEST, LootTables.UNDERWATER_RUIN_BIG_CHEST);
        loot(BuiltinThemes.MECHANICAL, LootTables.JUNGLE_TEMPLE_CHEST, LootTables.SIMPLE_DUNGEON_CHEST, LootTables.STRONGHOLD_CROSSING_CHEST);
    }

    private static void loot(Theme theme, Identifier... tables) {
        Stream.of(tables).map(LootTableAdditions::create).forEach(addition -> addition.add(theme.getLootTableId()).register());
    }

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Thematic.MOD_ID, id), item);
    }

    private static Item unstackable(String id, ItemFactory<Item> factory) {
        return register(id, factory.create(new FabricItemSettings().group(Thematic.getItemGroup()).maxCount(1)));
    }

    private static Item theme(String id, Theme theme) {
        return unstackable(id, s -> new ThemeItem(theme, s));
    }

    @FunctionalInterface interface ItemFactory<T extends Item> { T create(FabricItemSettings settings); }
}
