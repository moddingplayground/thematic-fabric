package net.moddingplayground.thematic.impl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
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

    @Override
    public void onInitializeClient() {
        this.logger.info("Initializing {}-client", MOD_NAME);
        Thematic.DECORATABLE_REGISTRY.forEach(Decoratable::registerClient);
    }

    public static ThematicClientImpl getInstance() {
        return instance;
    }
}
