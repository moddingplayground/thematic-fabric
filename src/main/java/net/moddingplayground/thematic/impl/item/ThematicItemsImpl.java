package net.moddingplayground.thematic.impl.item;

import net.fabricmc.api.ModInitializer;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.loottables.v0.LootTableAdditions;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.item.ThematicItems;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.stream.Stream;

public final class ThematicItemsImpl implements ThematicItems, ModInitializer {
    @Override
    public void onInitialize() {
        loot(BuiltinThemes.RUSTIC, LootTables.ABANDONED_MINESHAFT_CHEST, LootTables.PILLAGER_OUTPOST_CHEST, LootTables.WOODLAND_MANSION_CHEST);
        loot(BuiltinThemes.SUNKEN, LootTables.SHIPWRECK_TREASURE_CHEST, LootTables.BURIED_TREASURE_CHEST, LootTables.UNDERWATER_RUIN_SMALL_CHEST, LootTables.UNDERWATER_RUIN_BIG_CHEST);
        loot(BuiltinThemes.MECHANICAL, LootTables.JUNGLE_TEMPLE_CHEST, LootTables.SIMPLE_DUNGEON_CHEST, LootTables.STRONGHOLD_CROSSING_CHEST);
    }

    private static void loot(Theme theme, Identifier... tables) {
        Stream.of(tables).map(LootTableAdditions::create).forEach(addition -> addition.add(theme.getLootTableId()).register());
    }
}
