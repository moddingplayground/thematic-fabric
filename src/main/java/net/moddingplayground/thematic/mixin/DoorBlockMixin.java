package net.moddingplayground.thematic.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.moddingplayground.thematic.api.block.GateBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
    @Shadow @Final public static BooleanProperty OPEN;
    @Shadow @Final public static DirectionProperty FACING;
    @Shadow @Final public static EnumProperty<DoubleBlockHalf> HALF;
    @Shadow @Final public static EnumProperty<DoorHinge> HINGE;

    @Inject(method = "onUse", at = @At("TAIL"))
    private void onOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        this.thematic_synchronizeGate(world, pos, world.getBlockState(pos));
    }

    @Inject(
        method = "neighborUpdate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
            shift = At.Shift.AFTER
        )
    )
    private void onNeighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify, CallbackInfo ci) {
        this.thematic_synchronizeGate(world, pos, world.getBlockState(pos));
    }

    /**
     * Synchronizes a gate's block state if one is present above the door.
     */
    @Unique
    private void thematic_synchronizeGate(World world, BlockPos pos, BlockState state) {
        BlockPos posg = state.get(HALF) == DoubleBlockHalf.UPPER ? pos.up() : pos.up(2);
        BlockState stateg = world.getBlockState(posg);
        if (stateg.getBlock() instanceof GateBlock && GateBlock.isCompatibleDoor(state, stateg)) {
            world.setBlockState(posg, stateg.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS);
        }
    }
}
