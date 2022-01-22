package net.moddingplayground.thematic.datagen;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.block.ThematicBlocks;
import net.moddingplayground.toymaker.api.generator.model.item.AbstractItemModelGenerator;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

public class ItemModelGenerator extends AbstractItemModelGenerator {
    public ItemModelGenerator() {
        super(Thematic.MOD_ID);
    }

    @Override
    public void generate() {
        ThematicBlocks.forEach((theme, decoratable, block) -> {
            if (decoratable == LADDER) {
                this.add(block, this::generatedBlock);
            } else if (decoratable == LANTERN) {
                this.add(block, this::generatedItem);
            } else this.add(block, this::inherit);
        });

        for (Block block : Registry.BLOCK) {
            if (block.asItem() == Items.AIR) continue;
            Identifier id = Registry.BLOCK.getId(block);
            if (id.getNamespace().equals(Thematic.MOD_ID)) {
                if (!this.map.containsKey(id)) this.block(block);
            }
        }
    }
}
