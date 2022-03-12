package net.moddingplayground.thematic.impl.data;

import net.minecraft.block.Block;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.ModelGen;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.StateGen;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.StateModelInfo;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.VariantsStateGen;
import net.moddingplayground.thematic.api.Thematic;
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
        this.add(GATE, b -> gate(this, b));

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

    public static StateGen gate(AbstractStateModelGenerator gen, Block block) {
        VariantsStateGen state = VariantsStateGen.variants();

        Identifier tex = gen.name(block);
        Identifier texsides = gen.name(block, "block/%s_sides");
        ModelGen[] gens = {
            gate(tex, texsides, false),
            gate(tex, texsides, true)
        };

        for (Direction facing : Direction.Type.HORIZONTAL) {
            int baseRot = 0;

            if (facing == Direction.SOUTH) baseRot = 90;
            if (facing == Direction.WEST) baseRot = 180;
            if (facing == Direction.NORTH) baseRot = 270;

            for (DoorHinge hinge : DoorHinge.values()) {
                boolean right = hinge == DoorHinge.RIGHT;

                for (int i = 0; i < 2; i++) {
                    boolean open = (i & 1) == 0;

                    int rotDelta = 0;
                    if (open) rotDelta = right ? -90 : 90;

                    int rotation = (baseRot + rotDelta) % 360;
                    if (rotation < 0) rotation += 360;

                    String variant = "facing=" + facing.asString() + ","
                        + "hinge=" + hinge.asString() + ","
                        + "open=" + open;

                    boolean shouldHinge = right ^ open;
                    Identifier model = Identifier.tryParse(tex + (shouldHinge ? "_hinge" : ""));
                    int gi = shouldHinge ? 0 : 1;
                    ModelGen modelGen = gens[gi];
                    gens[gi] = null;

                    state.variant(variant, StateModelInfo.create(model, modelGen).rotate(0, rotation));
                }
            }
        }

        return state;
    }

    public static InheritingModelGen gate(Identifier gate, Identifier sides, boolean hinge) {
        return new InheritingModelGen(new Identifier(Thematic.MOD_ID, "block/template_gate" + (hinge ? "_hinge" : "")))
            .texture("gate", gate)
            .texture("sides", sides);
    }
}
