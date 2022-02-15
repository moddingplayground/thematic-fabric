package net.moddingplayground.thematic.impl.client;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.BlockEntityDecoratableData;
import net.moddingplayground.thematic.impl.client.model.ThematicEntityModelLayers;
import net.moddingplayground.thematic.impl.client.render.block.entity.MechanicalChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.RusticChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.SunkenChestBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class ThematicClientImpl implements Thematic, ClientModInitializer {
    private static ThematicClientImpl instance = null;
    protected final InitializationLogger initializer;

    public ThematicClientImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME, EnvType.CLIENT);
        instance = this;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        this.initializer.start();

        Thematic.DECORATABLE_REGISTRY.forEach(Decoratable::registerClient);

        Reflection.initialize(ThematicEntityModelLayers.class);
        this.registerChestRenderer(BuiltinThemes.RUSTIC, RusticChestBlockEntityRenderer::new);
        this.registerChestRenderer(BuiltinThemes.SUNKEN, SunkenChestBlockEntityRenderer::new);
        this.registerChestRenderer(BuiltinThemes.MECHANICAL, MechanicalChestBlockEntityRenderer::new);

        this.initializer.finish();
    }

    public void registerChestRenderer(Theme theme, BlockEntityRendererFactory<ChestBlockEntity> renderer) {
        BlockEntityRendererRegistry.register(BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.CHEST), renderer);
    }

    public static ThematicClientImpl getInstance() {
        return instance;
    }
}
