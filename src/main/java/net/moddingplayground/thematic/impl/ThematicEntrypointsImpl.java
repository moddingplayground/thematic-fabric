package net.moddingplayground.thematic.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.ThematicEntrypoint;

import java.util.List;

public final class ThematicEntrypointsImpl implements ModInitializer, Thematic {
    private final InitializationLogger initializer;

    public ThematicEntrypointsImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME + "-ENTRYPOINTS");
    }

    @Override
    public void onInitialize() {
        this.initializer.start();

        // initialize other mods' implementations
        FabricLoader loader = FabricLoader.getInstance();
        List<EntrypointContainer<ThematicEntrypoint>> entrypoints = loader.getEntrypointContainers(Thematic.MOD_ID, ThematicEntrypoint.class);
        if (!entrypoints.isEmpty()) {
            for (EntrypointContainer<ThematicEntrypoint> container : entrypoints) {
                ModContainer provider = container.getProvider();
                ModMetadata metadata = provider.getMetadata();
                LOGGER.info("   Initializing {}-{}", MOD_ID, metadata.getId());

                ThematicEntrypoint entrypoint = container.getEntrypoint();
                entrypoint.onInitializeThematic();
            }
        }

        this.initializer.finish();
    }
}
