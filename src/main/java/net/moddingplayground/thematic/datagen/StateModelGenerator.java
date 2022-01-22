package net.moddingplayground.thematic.datagen;

import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.block.ThematicBlocks;
import net.moddingplayground.toymaker.api.generator.model.InheritingModelGen;
import net.moddingplayground.toymaker.api.generator.model.StateGen;
import net.moddingplayground.toymaker.api.generator.model.StateModelInfo;
import net.moddingplayground.toymaker.api.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.toymaker.api.generator.model.block.VariantsStateGen;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

public class StateModelGenerator extends AbstractStateModelGenerator {
    public StateModelGenerator() {
        super(Thematic.MOD_ID);
    }

    @Override
    public void generate() {
        ThematicBlocks.forEach((theme, decoratable, block) -> {
            if (decoratable == LADDER) {
                this.add(block, b -> facingHorizontalRotated(name(b), ladder(name(b))));
            } else if (decoratable == LANTERN) {
                this.add(block, b -> lantern(name(b)));
            } else this.add(block);
        });
    }

    public static InheritingModelGen ladder(Identifier texture) {
        return new InheritingModelGen("block/ladder")
            .texture("texture", texture)
            .texture("particle", "#texture");
    }

    public StateGen lantern(Identifier name) {
        return VariantsStateGen.variants()
            .variant("hanging=false", StateModelInfo.create(name))
            .variant("hanging=true", StateModelInfo.create(Identifier.tryParse(name + "_hanging")));
    }
}
