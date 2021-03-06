package net.moddingplayground.thematic.impl.data;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractBlockLootTableGenerator;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import static net.moddingplayground.thematic.api.block.ThematicBlocks.*;

public class BlockLootTableGenerator extends AbstractBlockLootTableGenerator {
    public BlockLootTableGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        this.add(DECORATORS_TABLE);
        this.add(SEAT);
        this.add(GATE);

        for (Theme theme : ThematicRegistry.THEME) {
            for (Decoratable decoratable : ThematicRegistry.DECORATABLE) {
                decoratable.getData(theme, DecoratableDataToymaker.class).ifPresent(t -> t.generateBlockLootTables(this));
            }

            this.add(theme.getLootTableId(), LootTable.builder().pool(pool().with(ItemEntry.builder(theme.getItem()).conditionally(chance(0.2F)))));
        }
    }
}
