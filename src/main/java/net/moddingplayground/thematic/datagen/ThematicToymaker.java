package net.moddingplayground.thematic.datagen;

import net.minecraft.loot.context.LootContextTypes;
import net.moddingplayground.toymaker.api.ToymakerEntrypoint;
import net.moddingplayground.toymaker.api.registry.generator.ItemModelGeneratorStore;
import net.moddingplayground.toymaker.api.registry.generator.LootGeneratorStore;
import net.moddingplayground.toymaker.api.registry.generator.StateModelGeneratorStore;
import net.moddingplayground.toymaker.api.registry.generator.TagGeneratorStore;

public class ThematicToymaker implements ToymakerEntrypoint {
    @Override
    public void onInitializeToymaker() {
        StateModelGeneratorStore.register(StateModelGenerator::new);
        ItemModelGeneratorStore.register(ItemModelGenerator::new);
        LootGeneratorStore.register(BlockLootTableGenerator::new, LootContextTypes.BLOCK);
        TagGeneratorStore.register(BlockTagGenerator::new);
    }
}
