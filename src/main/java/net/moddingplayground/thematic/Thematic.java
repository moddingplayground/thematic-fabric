package net.moddingplayground.thematic;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thematic implements ModInitializer {
    public static final String MOD_ID   = "thematic";
    public static final String MOD_NAME = "Thematic";
    public static final Logger LOGGER   = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing {}", MOD_NAME);

        //

		LOGGER.info("Initialized {}", MOD_NAME);
	}
}
