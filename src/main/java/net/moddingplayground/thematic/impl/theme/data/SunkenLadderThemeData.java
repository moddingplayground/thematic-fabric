package net.moddingplayground.thematic.impl.theme.data;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.function.Consumer;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.block.BuildingBlocks.*;

public class SunkenLadderThemeData extends LadderThemeData {
    public SunkenLadderThemeData(Theme theme, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, modifier, wooden);
    }

    public static SunkenLadderThemeData of(Theme theme, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        return new SunkenLadderThemeData(theme, modifier, wooden);
    }

    @Override
    public void generateStateModels(AbstractStateModelGenerator gen) {
        Block block = this.getBlock();
        gen.add(block, b -> ladderVarying(gen.name(b), 3));
    }
}
