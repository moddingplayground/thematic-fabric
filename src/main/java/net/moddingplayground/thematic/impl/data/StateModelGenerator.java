package net.moddingplayground.thematic.impl.data;

import net.minecraft.block.Block;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.StateGen;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.StateModelInfo;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.VariantsStateGen;
import net.moddingplayground.thematic.api.block.ThematicSeatBlock;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen.*;
import static net.moddingplayground.frame.api.toymaker.v0.generator.model.block.BuildingBlocks.*;
import static net.moddingplayground.thematic.api.block.ThematicBlocks.*;

public class StateModelGenerator extends AbstractStateModelGenerator {
    public StateModelGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        this.add(DECORATORS_TABLE, b -> simple(name(b), cube(
            name(b, "block/%s_front"),
            name(b, "block/%s_east"),
            name(b, "block/%s_front"),
            name(b, "block/%s_west"),
            name(b, "block/%s_top"),
            name(b, "block/%s_bottom")
        )));

        this.add(SEAT, b -> seat(this, b));

        for (Theme theme : ThematicRegistry.THEME) {
            for (Decoratable decoratable : ThematicRegistry.DECORATABLE) {
                decoratable.getData(theme, DecoratableDataToymaker.class).ifPresent(t -> t.generateStateModels(this));
            }
        }
    }

    public static StateGen seat(AbstractStateModelGenerator gen, Block block) {
        return Util.make(VariantsStateGen.variants(), state -> {
            for (ThematicSeatBlock.Type type : ThematicSeatBlock.Type.values()) {
                String suffix = type == ThematicSeatBlock.Type.SINGLE ? "" : "_%s".formatted(type.getOpposite().asString());
                for (Direction direction : Direction.values()) {
                    if (direction.getAxis() != Direction.Axis.Y) {
                        state.variant(
                            "type=%s,facing=%s".formatted(type.asString(), direction.asString()),
                            DIRECTION_MODEL.apply(direction, StateModelInfo.create(gen.name(block, "block/%s" + suffix))).uvlock(false)
                        );
                    }
                }
            }
        });
    }
}
