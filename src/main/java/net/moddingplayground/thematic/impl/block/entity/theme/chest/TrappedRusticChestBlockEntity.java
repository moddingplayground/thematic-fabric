package net.moddingplayground.thematic.impl.block.entity.theme.chest;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.block.entity.theme.chest.TrappedThemedChestBlockEntity;
import net.moddingplayground.thematic.api.theme.Theme;

public class TrappedRusticChestBlockEntity extends TrappedThemedChestBlockEntity {
    public TrappedRusticChestBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public Theme getTheme() {
        return BuiltinThemes.RUSTIC;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        RusticChestBlockEntity.updateTreasureState(this);
    }
}
