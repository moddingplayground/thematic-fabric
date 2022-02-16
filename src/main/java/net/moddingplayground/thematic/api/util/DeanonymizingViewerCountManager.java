package net.moddingplayground.thematic.api.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.moddingplayground.thematic.mixin.ViewerCountManagerInvoker;

public class DeanonymizingViewerCountManager extends ViewerCountManager {
    private final ViewerCountManagerInvoker invoker;

    public DeanonymizingViewerCountManager(ViewerCountManager other) {
        this.invoker = (ViewerCountManagerInvoker) other;
    }

    @Override
    protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
        this.invoker.invokeOnContainerOpen(world, pos, state);
    }

    @Override
    protected void onContainerClose(World world, BlockPos pos, BlockState state) {
        this.invoker.invokeOnContainerClose(world, pos, state);
    }

    @Override
    protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        this.invoker.invokeOnViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
    }

    @Override
    protected boolean isPlayerViewing(PlayerEntity player) {
        return this.invoker.invokeIsPlayerViewing(player);
    }
}
