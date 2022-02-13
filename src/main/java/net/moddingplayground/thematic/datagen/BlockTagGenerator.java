package net.moddingplayground.thematic.datagen;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.block.ThematicBlocks;

import java.util.List;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

public class BlockTagGenerator extends AbstractTagGenerator<Block> {
    public BlockTagGenerator() {
        super(Thematic.MOD_ID, Registry.BLOCK);
    }

    @Override
    public void generate() {
        ThematicBlocks.forEach((theme, decoratable, block) -> {
            if (decoratable == LADDER) {
                this.add(BlockTags.CLIMBABLE, block);
            } else if (decoratable == LANTERN) {
                this.add(BlockTags.PICKAXE_MINEABLE, block);
            }

            if (List.of(LADDER, BOOKSHELF).contains(decoratable)) {
                this.add(theme.isMetallic() ? BlockTags.PICKAXE_MINEABLE : BlockTags.AXE_MINEABLE, block);
            }
        });
    }
}
