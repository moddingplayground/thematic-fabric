package net.moddingplayground.thematic.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ViewerCountManager.class)
public interface ViewerCountManagerInvoker {
    @Invoker void invokeOnContainerOpen(World world, BlockPos pos, BlockState state);
    @Invoker void invokeOnContainerClose(World world, BlockPos pos, BlockState state);
    @Invoker void invokeOnViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount);
    @Invoker boolean invokeIsPlayerViewing(PlayerEntity player);
}
