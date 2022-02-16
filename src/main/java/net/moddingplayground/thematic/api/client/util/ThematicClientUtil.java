package net.moddingplayground.thematic.api.client.util;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.BlockEntityDecoratableData;

public class ThematicClientUtil {
    public static void registerChestRenderer(Theme theme, BlockEntityRendererFactory<ChestBlockEntity> renderer) {
        BlockEntityRendererRegistry.register(BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.CHEST), renderer);
        BlockEntityRendererRegistry.register(BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.TRAPPED_CHEST), renderer);
    }
}
