package net.moddingplayground.thematic.api.theme.data.preset;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractBlockLootTableGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.item.ThemedBlockItem;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import java.util.Optional;
import java.util.function.Supplier;

public class BlockItemDecoratableData implements DecoratableData, DecoratableDataToymaker {
    private final Theme theme;
    protected Supplier<Block> block;
    protected Supplier<Item> item;

    public BlockItemDecoratableData(Theme theme, BlockFactory block, ItemFactory item) {
        this.theme = theme;
        if (block != null) this.block = Suppliers.memoize(block::create);
        if (item != null)  this.item  = Suppliers.memoize(() -> item.create(theme, this.getBlock(), new FabricItemSettings().group(Thematic.getItemGroup())));
    }

    public BlockItemDecoratableData(Theme theme, BlockFactory block) {
        this(theme, block, ThemedBlockItem::new);
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    public Block getBlock() {
        return Optional.ofNullable(this.block).map(Supplier::get).orElse(null);
    }

    public static Block getBlock(Theme theme, Decoratable decoratable) {
        return decoratable.getData(theme, BlockItemDecoratableData.class)
                          .orElseThrow()
                          .getBlock();
    }

    public Item getItem() {
        return Optional.ofNullable(this.item).map(Supplier::get).orElse(null);
    }

    public static Item getItem(Theme theme, Decoratable decoratable) {
        return decoratable.getData(theme, BlockItemDecoratableData.class)
                          .orElseThrow()
                          .getItem();
    }

    @Override
    public Identifier createId(Decoratable decoratable) {
        Theme theme = this.getTheme();
        Identifier id = theme.getId();
        return new Identifier(id.getNamespace(), theme.format(decoratable));
    }

    @Override
    public void register(Decoratable decoratable) {
        Identifier id = this.createId(decoratable);

        Registry.register(Registry.BLOCK, id, this.getBlock());
        Item item = this.getItem();
        if (item != null) Registry.register(Registry.ITEM, id, item);
    }

    @Override
    public void generateStateModels(AbstractStateModelGenerator gen) {
        Block block = this.getBlock();
        gen.add(block);
    }

    @Override
    public void generateItemModels(AbstractItemModelGenerator gen) {
        Item item = this.getItem();
        gen.add(item, gen::inherit);
    }

    @Override
    public void generateBlockLootTables(AbstractBlockLootTableGenerator gen) {
        gen.add(this.getBlock());
    }

    public static final BlockFactory NO_ITEM = () -> null;

    @FunctionalInterface public interface BlockFactory { Block create(); }
    @FunctionalInterface public interface ItemFactory { Item create(Theme theme, Block block, Item.Settings settings); }
}
