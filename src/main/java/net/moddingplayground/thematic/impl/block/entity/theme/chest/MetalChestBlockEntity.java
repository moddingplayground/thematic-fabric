package net.moddingplayground.thematic.impl.block.entity.theme.chest;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.item.Themed;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.BlockEntityDecoratableData;
import net.moddingplayground.thematic.api.util.ChestSoundViewerCountManager;
import net.moddingplayground.thematic.impl.sound.ThematicSoundEvents;

public abstract class MetalChestBlockEntity extends ChestBlockEntity implements Themed {
    public MetalChestBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
        ChestSoundViewerCountManager.replace(this, MetalChestBlockEntity::getOpenSound, MetalChestBlockEntity::getCloseSound);
    }

    public static SoundEvent getOpenSound(World world, BlockPos pos, BlockState state) {
        return ThematicSoundEvents.BLOCK_CHEST_METAL_OPEN;
    }

    public static SoundEvent getCloseSound(World world, BlockPos pos, BlockState state) {
        return ThematicSoundEvents.BLOCK_CHEST_METAL_CLOSE;
    }

    @Override
    public BlockEntityType<?> getType() {
        Theme theme = this.getTheme();
        return BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.CHEST);
    }
}
