package net.moddingplayground.thematic.api.theme.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractBlockLootTableGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.recipe.AbstractRecipeGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;

public interface DecoratableDataToymaker {
    default void generateItemModels(AbstractItemModelGenerator gen) {}
    default void generateRecipes(AbstractRecipeGenerator gen) {}
    default void generateStateModels(AbstractStateModelGenerator gen) {}
    default void generateBlockLootTables(AbstractBlockLootTableGenerator gen) {}
    default void generateBlockTags(AbstractTagGenerator<Block> gen) {}
    default void generateItemTags(AbstractTagGenerator<Item> gen) {}
}
