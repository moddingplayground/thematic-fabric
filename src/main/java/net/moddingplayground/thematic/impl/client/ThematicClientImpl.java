package net.moddingplayground.thematic.impl.client;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.impl.client.model.ThematicEntityModelLayers;

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

        this.initializer.finish();
    }
}
