package net.moddingplayground.thematic.impl.datagen;

import net.minecraft.loot.context.LootContextTypes;
import net.moddingplayground.frame.api.toymaker.v0.ToymakerEntrypoint;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.ItemModelGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.LootGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.StateModelGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.TagGeneratorStore;
import net.moddingplayground.thematic.api.Thematic;

public class ThematicToymakerImpl implements Thematic, ToymakerEntrypoint {
    @Override
    public void onInitializeToymaker() {
        StateModelGeneratorStore.register(() -> new StateModelGenerator(MOD_ID));
        ItemModelGeneratorStore.register(() -> new ItemModelGenerator(MOD_ID));
        LootGeneratorStore.register(() -> new BlockLootTableGenerator(MOD_ID), LootContextTypes.BLOCK);
        TagGeneratorStore.register(() -> new BlockTagGenerator(MOD_ID));
    }
}
