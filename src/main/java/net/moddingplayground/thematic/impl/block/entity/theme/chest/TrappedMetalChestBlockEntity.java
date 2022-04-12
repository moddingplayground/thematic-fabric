package net.moddingplayground.thematic.impl.block.entity.theme.chest;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.Themed;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.BlockEntityDecoratableData;
import net.moddingplayground.thematic.api.util.ChestSoundViewerCountManager;

public abstract class TrappedMetalChestBlockEntity extends TrappedChestBlockEntity implements Themed {
    public TrappedMetalChestBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        ChestSoundViewerCountManager.replace(this, MetalChestBlockEntity::getOpenSound, MetalChestBlockEntity::getCloseSound);
    }

    @Override
    public BlockEntityType<?> getType() {
        Theme theme = this.getTheme();
        return BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.TRAPPED_CHEST);
    }
}
