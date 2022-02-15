package net.moddingplayground.thematic.impl.client;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class ThematicClientImpl implements Thematic, ClientModInitializer {
    private static ThematicClientImpl instance = null;
    protected final Logger logger;

    public ThematicClientImpl() {
        this.logger = LoggerFactory.getLogger("%s-client".formatted(MOD_ID));
        instance = this;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        this.logger.info("Initializing {}-client", MOD_NAME);
        Thematic.DECORATABLE_REGISTRY.forEach(Decoratable::registerClient);

        Reflection.initialize(ThematicEntityModelLayers.class);

        this.registerChestRenderer(BuiltinThemes.RUSTIC, RusticChestBlockEntityRenderer::new);
        this.registerChestRenderer(BuiltinThemes.SUNKEN, SunkenChestBlockEntityRenderer::new);
        this.registerChestRenderer(BuiltinThemes.MECHANICAL, MechanicalChestBlockEntityRenderer::new);
    }

    public void registerChestRenderer(Theme theme, BlockEntityRendererFactory<ChestBlockEntity> renderer) {
        BlockEntityRendererRegistry.register(BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.CHEST), renderer);
    }

    public static ThematicClientImpl getInstance() {
        return instance;
    }
}
