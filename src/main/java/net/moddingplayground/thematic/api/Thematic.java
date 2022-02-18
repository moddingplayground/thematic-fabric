package net.moddingplayground.thematic.api;

import net.minecraft.item.ItemGroup;
import net.moddingplayground.thematic.impl.ThematicImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entrypoint helper for Thematic.
 */
public interface Thematic {
    String MOD_ID   = "thematic";
    String MOD_NAME = "Thematic";
    Logger LOGGER   = LoggerFactory.getLogger(MOD_ID);

    static ItemGroup getItemGroup() {
        return ThematicImpl.getInstance().getItemGroup();
    }
}
