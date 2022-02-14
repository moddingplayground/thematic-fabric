package net.moddingplayground.thematic.impl.datagen;

import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.impl.block.ThematicBlocks;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

public class ItemModelGenerator extends AbstractItemModelGenerator {
    public ItemModelGenerator(String modId) {
        super(modId);
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
