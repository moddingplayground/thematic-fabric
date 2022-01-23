package net.moddingplayground.thematic.datagen;

import net.minecraft.item.BlockItem;
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

        Registry.ITEM.stream()
                     .filter(item -> {
                         Identifier id = Registry.ITEM.getId(item);
                         return !this.map.containsKey(id) && id.getNamespace().equals(Thematic.MOD_ID);
                     })
                     .forEach(item -> {
                         if (item instanceof BlockItem blockItem) this.block(blockItem.getBlock());
                         else this.add(item);
                     });
    }
}
