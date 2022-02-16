package net.moddingplayground.thematic.impl.client;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.client.util.ThematicClientUtil;
import net.moddingplayground.thematic.api.theme.Decoratable;
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
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.RUSTIC, RusticChestBlockEntityRenderer::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.SUNKEN, SunkenChestBlockEntityRenderer::new);
        ThematicClientUtil.registerChestRenderer(BuiltinThemes.MECHANICAL, MechanicalChestBlockEntityRenderer::new);

        this.initializer.finish();
    }

    public static ThematicClientImpl getInstance() {
        return instance;
    }
}
