package net.moddingplayground.thematic.api.theme.data;

import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractBlockLootTableGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.thematic.api.item.ThemedBlockItem;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.function.Supplier;

public class BlockItemThemeData implements ThemeData, ThemeDataToymaker {
    private final Theme theme;
    private final Supplier<Block> block;
    private final Supplier<Item> item;

    public BlockItemThemeData(Theme theme, BlockFactory block, ItemFactory item) {
        this.theme = theme;
        this.block = Suppliers.memoize(block::create);
        this.item = Suppliers.memoize(() -> item.create(theme, this.getBlock(), new FabricItemSettings()));
    }

    public BlockItemThemeData(Theme theme, BlockFactory block) {
        this(theme, block, ThemedBlockItem::new);
    }

    public static BlockItemThemeData of(Theme theme, BlockFactory block, ItemFactory item) {
        return new BlockItemThemeData(theme, block, item);
    }

    public static BlockItemThemeData of(Theme theme, BlockFactory block) {
        return new BlockItemThemeData(theme, block);
    }

    public Theme getTheme() {
        return this.theme;
    }

    public Block getBlock() {
        return this.block.get();
    }

    public Item getItem() {
        return this.item.get();
    }

    @Override
    public void register(Decoratable decoratable) {
        Theme theme = this.getTheme();
        Identifier themeId = theme.getId();

        String format = decoratable.getFormat();
        Identifier id = new Identifier(themeId.getNamespace(), format.formatted(themeId.getPath()));

        Registry.register(Registry.BLOCK, id, this.getBlock());
        Item item = this.getItem();
        if (item != null) Registry.register(Registry.ITEM, id, this.getItem());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerClient(Decoratable decoratable) {}

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
