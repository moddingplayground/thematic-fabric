package net.moddingplayground.thematic.api.theme.data;

import net.minecraft.block.Block;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractBlockLootTableGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;

public interface DecoratableDataToymaker {
    void generateItemModels(AbstractItemModelGenerator gen);
    void generateStateModels(AbstractStateModelGenerator gen);
    void generateBlockLootTables(AbstractBlockLootTableGenerator gen);

    default void generateBlockTags(AbstractTagGenerator<Block> gen) {}
}
