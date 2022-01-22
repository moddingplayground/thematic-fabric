package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.moddingplayground.thematic.Thematic;

public class Decoratable {
    private final String format;
    private final BlockFactory block;
    private final ItemFactory item;

    public Decoratable(String format, BlockFactory block, ItemFactory item) {
        this.format = format;
        this.block = block;
        this.item = item;
    }

    public Decoratable(String format, BlockFactory factory) {
        this(format, factory, (b, t) -> new BlockItem(b, new FabricItemSettings().group(Thematic.ITEM_GROUP)));
    }

    public Decoratable(String format, AbstractBlock.Settings settings) {
        this(format, t -> new Block(settings));
    }

    public String format(Theme theme) {
        return this.format.formatted(theme.getId());
    }

    public Block block(Theme theme) {
        return this.block.create(theme);
    }

    public Item item(Block block, Theme theme) {
        return this.item.create(block, theme);
    }

    @Override
    public String toString() {
        return "Decoratable{" + this.format.formatted("decoratable") + '}';
    }

    @FunctionalInterface public interface BlockFactory { Block create(Theme theme); }
    @FunctionalInterface public interface ItemFactory { Item create(Block block, Theme theme); }
}
