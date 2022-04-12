package net.moddingplayground.thematic.api.util;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Consumer;

public interface BlockSettingsFactory {
    Consumer<FabricBlockSettings> NETHERITE_REQUIRES_TOOL = requiresTool(BlockSoundGroup.NETHERITE);
    Consumer<FabricBlockSettings> COPPER_REQUIRES_TOOL = requiresTool(BlockSoundGroup.COPPER);

    Consumer<FabricBlockSettings> NETHERITE_REQUIRES_TOOL_STRONGER = requiresTool(BlockSoundGroup.NETHERITE, 3.5F);
    Consumer<FabricBlockSettings> COPPER_REQUIRES_TOOL_STRONGER = requiresTool(BlockSoundGroup.COPPER, 3.5F);

    Consumer<FabricBlockSettings> CHAIN_REQUIRES_TOOL_STRONGER = requiresTool(BlockSoundGroup.CHAIN, 3.5F);

    private static Consumer<FabricBlockSettings> requiresTool(BlockSoundGroup sounds, float strength) {
        return s -> s.sounds(sounds).requiresTool().strength(strength);
    }

    private static Consumer<FabricBlockSettings> requiresTool(BlockSoundGroup sounds) {
        return s -> s.sounds(sounds).requiresTool();
    }
}
