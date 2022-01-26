package net.moddingplayground.thematic.mixin.bookshelf;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.moddingplayground.thematic.block.ThematicBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean redirectRandomDisplayTickIsOf(BlockState state, Block block) {
        if (block == Blocks.BOOKSHELF && state.getBlock() instanceof ThematicBlock) return true;
        return state.isOf(block);
    }
}
