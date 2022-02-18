package net.moddingplayground.thematic.impl.block.entity.theme.chest;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.block.ThematicProperties;
import net.moddingplayground.thematic.api.sound.ThematicSoundEvents;
import net.moddingplayground.thematic.api.tag.ThematicItemTags;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.util.ChestSoundViewerCountManager;

import java.util.Set;

public class RusticChestBlockEntity extends MetalChestBlockEntity {
    public RusticChestBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        ChestSoundViewerCountManager.replace(this, RusticChestBlockEntity::getOpenSound, RusticChestBlockEntity::getCloseSound);
    }

    public static SoundEvent getOpenSound(World world, BlockPos pos, BlockState state) {
        return state.get(ThematicProperties.TREASURE) ? ThematicSoundEvents.BLOCK_RUSTIC_CHEST_TREASURE_OPEN : SoundEvents.BLOCK_CHEST_OPEN;
    }

    public static SoundEvent getCloseSound(World world, BlockPos pos, BlockState state) {
        return state.get(ThematicProperties.TREASURE) ? ThematicSoundEvents.BLOCK_RUSTIC_CHEST_TREASURE_CLOSE : SoundEvents.BLOCK_CHEST_CLOSE;
    }

    @Override
    public Theme getTheme() {
        return BuiltinThemes.RUSTIC;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        updateTreasureState(this);
    }

    public static void updateTreasureState(ChestBlockEntity blockEntity) {
        World world = blockEntity.getWorld();
        if (world.isClient) return;
        BlockState state = blockEntity.getCachedState();
        if (state.getBlock() instanceof ChestBlock chest) {
            BlockPos pos = blockEntity.getPos();
            Inventory inventory = ChestBlock.getInventory(chest, state, world, pos, true);
            Set<Item> set = Set.copyOf(ThematicItemTags.RUSTIC_TREASURE_ITEMS.values());
            world.setBlockState(pos, state.with(ThematicProperties.TREASURE, inventory.containsAny(set)));
        }
    }
}
