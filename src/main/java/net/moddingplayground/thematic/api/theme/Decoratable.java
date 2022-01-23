package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.moddingplayground.thematic.item.ThemedBlockItem;

public class Decoratable {
    private final String format;
    private final BlockFactory block;
    private final ItemFactory item;
    private final PostRegister postRegister;

    public Decoratable(String format, BlockFactory block, ItemFactory item, PostRegister postRegister) {
        this.format = format;
        this.block = block;
        this.item = item;
        this.postRegister = postRegister;
    }

    public Decoratable(String format, BlockFactory factory, PostRegister postRegister) {
        this(format, factory, (b, t) -> new ThemedBlockItem(t, b, new FabricItemSettings().group(t.getItemGroup())), postRegister);
    }

    public Decoratable(Decoratable other, BlockFactory factory, PostRegister postRegister) {
        this(other.getFormat(), factory, postRegister);
    }

    public Decoratable(Decoratable other, BlockFactory factory) {
        this(other, factory, PostRegister.NONE);
    }

    public Decoratable(Decoratable other, PostRegister postRegister) {
        this(other, other.block, postRegister);
    }

    public Decoratable(String format, AbstractBlock.Settings settings) {
        this(format, t -> new Block(settings), PostRegister.NONE);
    }

    public String getFormat() {
        return this.format;
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

    public PostRegister getPostRegister() {
        return this.postRegister;
    }

    @Override
    public String toString() {
        return "Decoratable{" + this.format.formatted("decoratable") + '}';
    }

    @FunctionalInterface public interface BlockFactory { Block create(Theme theme); }
    @FunctionalInterface public interface ItemFactory { Item create(Block block, Theme theme); }

    @FunctionalInterface
    public interface PostRegister {
        PostRegister NONE = (t, d, b) -> {};
        void apply(Theme theme, Decoratable decoratable, Block block);
    }
}
