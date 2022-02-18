package net.moddingplayground.thematic.api.block;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public interface ThematicProperties {
    /**
     * A property that determines whether a rustic chest contains treasure.
     */
    BooleanProperty TREASURE = BooleanProperty.of("treasure");

    /**
     * A property that determines the type of seat.
     */
    EnumProperty<ThematicSeatBlock.Type> SEAT_TYPE = EnumProperty.of("type", ThematicSeatBlock.Type.class);
}
