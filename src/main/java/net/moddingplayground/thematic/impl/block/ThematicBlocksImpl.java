package net.moddingplayground.thematic.impl.block;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.moddingplayground.thematic.api.block.ThematicBlocks;

public final class ThematicBlocksImpl implements ThematicBlocks, ModInitializer {
    @Override
    public void onInitialize() {
        FuelRegistry fuel = FuelRegistry.INSTANCE;
        fuel.add(SEAT, 300);
        fuel.add(GATE, 200);

        FlammableBlockRegistry flammable = FlammableBlockRegistry.getDefaultInstance();
        flammable.add(SEAT, 30, 20);
    }
}
