package net.moddingplayground.thematic.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChestBlockEntity.class)
public interface ChestBlockEntityInvoker {
    @Invoker static void invokePlaySound(World world, BlockPos pos, BlockState state, SoundEvent sound) { throw new AssertionError(); }
}
