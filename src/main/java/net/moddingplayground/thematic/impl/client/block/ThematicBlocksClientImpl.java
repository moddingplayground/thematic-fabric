package net.moddingplayground.thematic.impl.client.block;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.block.ThematicBlocks;
import net.moddingplayground.thematic.api.client.screen.DecoratorsTableScreen;
import net.moddingplayground.thematic.api.client.util.ThematicClientUtil;
import net.moddingplayground.thematic.api.screen.ThematicScreenHandlerType;
import net.moddingplayground.thematic.impl.client.render.block.entity.MechanicalChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.RusticChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.SunkenChestBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public final class ThematicBlocksClientImpl implements ThematicBlocks, ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap renderLayers = BlockRenderLayerMap.INSTANCE;
        renderLayers.putBlocks(RenderLayer.getCutout(), ThematicBlocks.SEAT, ThematicBlocks.GATE);

        HandledScreens.register(ThematicScreenHandlerType.DECORATORS_TABLE, DecoratorsTableScreen::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.RUSTIC, RusticChestBlockEntityRenderer::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.SUNKEN, SunkenChestBlockEntityRenderer::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.MECHANICAL, MechanicalChestBlockEntityRenderer::new);
    }
}
