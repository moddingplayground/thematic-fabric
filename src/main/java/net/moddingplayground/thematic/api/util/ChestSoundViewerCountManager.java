package net.moddingplayground.thematic.api.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.moddingplayground.thematic.mixin.ChestBlockEntityAccessor;
import net.moddingplayground.thematic.mixin.ChestBlockEntityInvoker;

public class ChestSoundViewerCountManager extends DeanonymizingViewerCountManager {
    private final SoundFactory open, close;

    public ChestSoundViewerCountManager(ViewerCountManager other, SoundFactory open, SoundFactory close) {
        super(other);
        this.open = open;
        this.close = close;
    }

    @Override
    protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
        play(world, pos, state, this.open);
    }

    @Override
    protected void onContainerClose(World world, BlockPos pos, BlockState state) {
        play(world, pos, state, this.close);
    }

    public void play(World world, BlockPos pos, BlockState state, SoundFactory factory) {
        SoundEvent sound = factory.get(world, pos, state);
        ChestBlockEntityInvoker.invokePlaySound(world, pos, state, sound);
    }

    public static void replace(ChestBlockEntity chest, SoundFactory open, SoundFactory close) {
        ChestBlockEntityAccessor access = (ChestBlockEntityAccessor) chest;
        access.setStateManager(new ChestSoundViewerCountManager(access.getStateManager(), open, close));
    }

    @FunctionalInterface public interface SoundFactory { SoundEvent get(World world, BlockPos pos, BlockState state); }
}
