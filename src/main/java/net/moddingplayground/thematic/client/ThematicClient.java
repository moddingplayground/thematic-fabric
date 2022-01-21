package net.moddingplayground.thematic.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.moddingplayground.thematic.Thematic.*;

public class ThematicClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("%s-client".formatted(MOD_ID));

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing {}-client", MOD_NAME);

        //

        LOGGER.info("Initialized {}-client", MOD_NAME);
    }
}
