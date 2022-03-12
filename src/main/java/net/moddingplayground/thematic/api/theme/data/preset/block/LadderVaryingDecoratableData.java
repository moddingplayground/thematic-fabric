package net.moddingplayground.thematic.api.theme.data.preset.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;

import java.util.function.Consumer;
import java.util.function.Function;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.block.BuildingBlocks.*;

public class LadderVaryingDecoratableData extends LadderDecoratableData {
    private final int variants;

    public LadderVaryingDecoratableData(Theme theme, int variants, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, modifier, wooden);
        this.variants = variants;
    }

    public LadderVaryingDecoratableData(Theme theme, int variants) {
        this(theme, variants, s -> {}, true);
    }

    public static Function<Theme, DecoratableData> createMetal(int variants, Consumer<FabricBlockSettings> modifier) {
        return theme -> new LadderVaryingDecoratableData(theme, variants, modifier, false);
    }

    public int getVariants() {
        return this.variants;
    }

    @Override
    public void generateStateModels(AbstractStateModelGenerator gen) {
        Block block = this.getBlock();
        int variants = this.getVariants();
        gen.add(block, b -> ladderVarying(gen.name(b), variants));
    }
}
