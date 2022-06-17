package net.moddingplayground.thematic.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.Thematic;
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

public final class ThematicImpl implements ModInitializer, Thematic {
    private final InitializationLogger initializer;

    public ThematicImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        this.initializer.start();

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
