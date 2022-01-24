package net.moddingplayground.thematic.datagen;

import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.block.ThematicBlocks;
import net.moddingplayground.toymaker.api.generator.model.InheritingModelGen;
import net.moddingplayground.toymaker.api.generator.model.ModelGen;
import net.moddingplayground.toymaker.api.generator.model.StateGen;
import net.moddingplayground.toymaker.api.generator.model.StateModelInfo;
import net.moddingplayground.toymaker.api.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.toymaker.api.generator.model.block.VariantsStateGen;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.IntFunction;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;
import static net.moddingplayground.thematic.api.theme.Theme.*;
import static net.moddingplayground.toymaker.api.generator.model.StateModelInfo.*;
import static net.moddingplayground.toymaker.api.generator.model.block.BuildingBlocks.*;

public class StateModelGenerator extends AbstractStateModelGenerator {
    public StateModelGenerator() {
        super(Thematic.MOD_ID);
    }

    @Override
    public void generate() {
        ThematicBlocks.forEach((theme, decoratable, block) -> {
            if (decoratable == LADDER) {
                if (theme == SUNKEN) {
                    this.add(block, b -> sunkenLadder(name(b)));
                } else this.add(block, b -> facingHorizontalRotated(name(b), ladder(name(b))));
            } else if (decoratable == LANTERN) {
                this.add(block, b -> lantern(name(b)));
            } else this.add(block);
        });
    }

    public static StateGen sunkenLadder(Identifier name) {
        int variants = 3;

        Identifier[] ids = indexedArray(variants, i -> i == 0 ? name : name(name, i), Identifier[]::new);
        ModelGen[] models = indexedArray(variants, i -> ladder(ids[i]), ModelGen[]::new);

        VariantsStateGen gen = VariantsStateGen.variants();
        for (Direction direction : Direction.values()) {
            if (direction.getAxis() != Direction.Axis.Y) {
                gen.variant("facing=" + direction.getName(), indexedArray(variants,
                    i -> DIRECTION_MODEL.apply(direction, StateModelInfo.create(ids[i], models[i]))
                                        .weight(variants - i), StateModelInfo[]::new
                ));
            }
        }

        return gen;
    }

    public static InheritingModelGen ladder(Identifier texture) {
        return new InheritingModelGen("block/ladder")
            .texture("texture", texture)
            .texture("particle", "#texture");
    }

    public StateGen lantern(Identifier name) {
        return VariantsStateGen.variants()
            .variant("hanging=false", create(name))
            .variant("hanging=true", create(Identifier.tryParse(name + "_hanging")));
    }

    public static <T> T[] indexedArray(int cap, Function<Integer, T> action, IntFunction<T[]> generator) {
        return Util.make(new ArrayList<T>(), list -> {
            for (int i = 0; i < cap; i++) list.add(action.apply(i));
        }).toArray(generator);
    }

    public static Identifier name(Identifier id, Object arg, Object... args) {
        StringBuilder str = Util.make(new StringBuilder(), b -> { for (Object s : args) b.append(s); });
        return Identifier.tryParse(id + arg.toString() + str);
    }
}