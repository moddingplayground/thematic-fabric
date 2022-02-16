package net.moddingplayground.thematic.api.block.entity.theme.chest;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.item.Themed;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.BlockEntityDecoratableData;

public abstract class TrappedThemedChestBlockEntity extends TrappedChestBlockEntity implements Themed {
    public TrappedThemedChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        Theme theme = this.getTheme();
        return BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.TRAPPED_CHEST);
    }
}
