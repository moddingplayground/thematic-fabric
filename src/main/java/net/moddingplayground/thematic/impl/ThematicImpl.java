package net.moddingplayground.thematic.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.impl.block.ThematicBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThematicImpl implements ModInitializer, Thematic {
    private static ThematicImpl instance = null;
    protected final Logger logger;

    public ThematicImpl() {
        this.logger = LoggerFactory.getLogger(MOD_ID);
        instance = this;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        this.logger.info("Initializing {}", MOD_NAME);
        Reflection.initialize(ThematicBlocks.class);
    }

    public static ThematicImpl getInstance() {
        return instance;
    }
}
