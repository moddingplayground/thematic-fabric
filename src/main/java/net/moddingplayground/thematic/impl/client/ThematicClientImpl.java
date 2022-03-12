package net.moddingplayground.thematic.impl.client;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.block.ThematicBlocks;
import net.moddingplayground.thematic.api.client.screen.DecoratorsTableScreen;
import net.moddingplayground.thematic.api.client.util.ThematicClientUtil;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.screen.ThematicScreenHandlerType;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.impl.client.model.ThematicEntityModelLayers;
import net.moddingplayground.thematic.impl.client.render.block.entity.MechanicalChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.RusticChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.SunkenChestBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public final class ThematicClientImpl implements Thematic, ClientModInitializer {
    private final InitializationLogger initializer;

    public ThematicClientImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME, EnvType.CLIENT);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        this.initializer.start();

        ThematicRegistry.DECORATABLE.forEach(Decoratable::registerClient);
        Reflection.initialize(ThematicEntityModelLayers.class);

        BlockRenderLayerMap renderLayers = BlockRenderLayerMap.INSTANCE;
        renderLayers.putBlock(ThematicBlocks.SEAT, RenderLayer.getCutout());

        ScreenRegistry.register(ThematicScreenHandlerType.DECORATORS_TABLE, DecoratorsTableScreen::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.RUSTIC, RusticChestBlockEntityRenderer::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.SUNKEN, SunkenChestBlockEntityRenderer::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.MECHANICAL, MechanicalChestBlockEntityRenderer::new);

        this.initializer.finish();
    }
}
