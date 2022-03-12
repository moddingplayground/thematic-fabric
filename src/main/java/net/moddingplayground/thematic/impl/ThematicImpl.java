package net.moddingplayground.thematic.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.ThematicEntrypoint;
import net.moddingplayground.thematic.api.block.ThematicBlocks;
import net.moddingplayground.thematic.api.item.ThematicItemGroups;
import net.moddingplayground.thematic.api.item.ThematicItems;
import net.moddingplayground.thematic.api.recipe.ThematicRecipeSerializer;
import net.moddingplayground.thematic.api.recipe.ThematicRecipeType;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.screen.ThematicScreenHandlerType;
import net.moddingplayground.thematic.api.sound.ThematicSoundEvents;
import net.moddingplayground.thematic.api.stat.ThematicStats;
import net.moddingplayground.thematic.api.theme.Decoratable;

import java.util.List;

public final class ThematicImpl implements ModInitializer, Thematic {
    private final InitializationLogger initializer;

    public ThematicImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        this.initializer.start();

        // initialize other mods' implementations
        FabricLoader loader = FabricLoader.getInstance();
        List<EntrypointContainer<ThematicEntrypoint>> entrypoints = loader.getEntrypointContainers(Thematic.MOD_ID, ThematicEntrypoint.class);
        if (!entrypoints.isEmpty()) {
            InitializationLogger entrypointLogger = new InitializationLogger(LOGGER, "%s-ENTRYPOINTS".formatted(MOD_NAME));
            entrypointLogger.start();

            for (EntrypointContainer<ThematicEntrypoint> container : entrypoints) {
                ModContainer provider = container.getProvider();
                ModMetadata metadata = provider.getMetadata();
                LOGGER.info("   Initializing {}-{}", MOD_ID, metadata.getId());

                ThematicEntrypoint entrypoint = container.getEntrypoint();
                entrypoint.onInitializeThematic();
            }

            entrypointLogger.finish();
        }

        // register base objects
        Reflection.initialize(
            ThematicStats.class, ThematicScreenHandlerType.class,
            ThematicRecipeType.class, ThematicRecipeSerializer.class,
            ThematicBlocks.class, ThematicItems.class,
            ThematicSoundEvents.class, ThematicItemGroups.class
        );

        // register all decoratables
        ThematicRegistry.DECORATABLE.forEach(Decoratable::register);

        this.initializer.finish();
    }
}
