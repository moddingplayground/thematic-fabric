package net.moddingplayground.thematic.impl.theme.data;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.LadderDecoratableData;

import java.util.function.Consumer;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.block.BuildingBlocks.*;

public class VaryingLadderDecoratableData extends LadderDecoratableData {
    private final int variants;

    public VaryingLadderDecoratableData(Theme theme, int variants, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, modifier, wooden);
        this.variants = variants;
    }

    public VaryingLadderDecoratableData(Theme theme, int variants) {
        this(theme, variants, s -> {}, true);
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
