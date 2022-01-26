package net.moddingplayground.thematic.mixin.ladder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FlowableFluid;
import net.moddingplayground.thematic.block.ThematicLadderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(FlowableFluid.class)
public class FlowableFluidMixin {
    @Redirect(method = "canFill", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"), slice = @Slice(to = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;LADDER:Lnet/minecraft/block/Block;", shift = At.Shift.BY, by = 3)))
    private boolean redirectCanFillIsOf(BlockState state, Block block) {
        if (block == Blocks.LADDER && state.getBlock() instanceof ThematicLadderBlock) return true;
        return state.isOf(block);
    }
}
