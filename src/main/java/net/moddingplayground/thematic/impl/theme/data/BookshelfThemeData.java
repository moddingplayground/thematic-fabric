package net.moddingplayground.thematic.impl.theme.data;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.BlockItemThemeData;

import java.util.function.Consumer;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen.*;

public class BookshelfThemeData extends BlockItemThemeData {
    private final boolean wooden;

    public BookshelfThemeData(Theme theme, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, () -> createBookshelf(modifier));
        this.wooden = wooden;
    }

    public static BookshelfThemeData of(Theme theme, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        return new BookshelfThemeData(theme, modifier, wooden);
    }

    public static BookshelfThemeData of(Theme theme, Consumer<FabricBlockSettings> modifier) {
        return of(theme, modifier, true);
    }

    public boolean isWooden() {
        return this.wooden;
    }

    @Override
    public void register(Decoratable decoratable) {
        super.register(decoratable);
        Block block = this.getBlock();

        if (this.isWooden()) {
            FuelRegistry fuel = FuelRegistry.INSTANCE;
            fuel.add(block, 300);
            FlammableBlockRegistry flammable = FlammableBlockRegistry.getDefaultInstance();
            flammable.add(block, 30, 20);
        }

        StateRegistry.BOOKSHELVES.add(block);
    }

    @Override
    public void generateStateModels(AbstractStateModelGenerator gen) {
        Block block = this.getBlock();
        gen.add(block, b -> {
            Identifier n = gen.name(b);
            return gen.simple(n, cubeColumn(Identifier.tryParse(n + "_top"), n));
        });
    }

    @Override
    public void generateBlockTags(AbstractTagGenerator<Block> gen) {
        Block block = this.getBlock();
        gen.add(this.isWooden() ? BlockTags.AXE_MINEABLE : BlockTags.PICKAXE_MINEABLE, block);
    }

    public static Block createBookshelf(Consumer<FabricBlockSettings> modifier) {
        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.BOOKSHELF);
        modifier.accept(settings);
        return new Block(settings);
    }
}
