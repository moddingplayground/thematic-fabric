package net.moddingplayground.thematic.api.block;

import net.minecraft.state.property.BooleanProperty;

public interface ThematicProperties {
    /**
     * A property that determines whether a rustic chest contains treasure.
     */
    BooleanProperty TREASURE = BooleanProperty.of("treasure");
}
