package net.moddingplayground.thematic.api.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

import java.util.function.Function;

public interface ThematicBlocks {
    Block DECORATORS_TABLE = register("decorators_table", new DecoratorsTableBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f).sounds(BlockSoundGroup.WOOD)));

    private static Block register(String id, Block block, Function<Block, Item> item) {
        Identifier identifier = new Identifier(Thematic.MOD_ID, id);
        if (item != null) Registry.register(Registry.ITEM, identifier, item.apply(block));
        return Registry.register(Registry.BLOCK, identifier, block);
    }

    private static Block register(String id, Block block) {
        return register(id, block, (b) -> new BlockItem(b, new FabricItemSettings().group(Thematic.getItemGroup())));
    }
}
