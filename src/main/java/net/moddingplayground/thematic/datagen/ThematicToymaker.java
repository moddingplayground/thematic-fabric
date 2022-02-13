package net.moddingplayground.thematic.datagen;

import net.minecraft.loot.context.LootContextTypes;
import net.moddingplayground.frame.api.toymaker.v0.ToymakerEntrypoint;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.ItemModelGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.LootGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.StateModelGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.TagGeneratorStore;

public class ThematicToymaker implements ToymakerEntrypoint {
    @Override
    public void onInitializeToymaker() {
        StateModelGeneratorStore.register(StateModelGenerator::new);
        ItemModelGeneratorStore.register(ItemModelGenerator::new);
        LootGeneratorStore.register(BlockLootTableGenerator::new, LootContextTypes.BLOCK);
        TagGeneratorStore.register(BlockTagGenerator::new);
    }
}
