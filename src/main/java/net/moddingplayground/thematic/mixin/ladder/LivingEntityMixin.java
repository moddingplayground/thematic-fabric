package net.moddingplayground.thematic.mixin.ladder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.moddingplayground.thematic.block.ThematicLadderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Redirect(method = "canEnterTrapdoor", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean redirectCanEnterTrapdoorIsOf(BlockState state, Block block) {
        if (block == Blocks.LADDER && state.getBlock() instanceof ThematicLadderBlock) return true;
        return state.isOf(block);
    }
}
