package net.moddingplayground.thematic.datagen;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.block.ThematicBlocks;
import net.moddingplayground.toymaker.api.generator.tag.AbstractTagGenerator;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

public class BlockTagGenerator extends AbstractTagGenerator<Block> {
    public BlockTagGenerator() {
        super(Thematic.MOD_ID, Registry.BLOCK);
    }

    @Override
    public void generate() {
        ThematicBlocks.forEach((theme, decoratable, block) -> {
            Theme.Data data = theme.getData();
            if (decoratable == LADDER) {
                this.add(BlockTags.CLIMBABLE, block);
                this.add(data.metallic() ? BlockTags.PICKAXE_MINEABLE : BlockTags.AXE_MINEABLE, block);
            } else if (decoratable == LANTERN) {
                this.add(BlockTags.PICKAXE_MINEABLE, block);
            }
        });
    }
}
