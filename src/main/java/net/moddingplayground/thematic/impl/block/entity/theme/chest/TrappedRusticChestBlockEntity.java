package net.moddingplayground.thematic.impl.block.entity.theme.chest;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.util.ChestSoundViewerCountManager;

public class TrappedRusticChestBlockEntity extends TrappedMetalChestBlockEntity {
    public TrappedRusticChestBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        ChestSoundViewerCountManager.replace(this, RusticChestBlockEntity::getOpenSound, RusticChestBlockEntity::getCloseSound);
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
