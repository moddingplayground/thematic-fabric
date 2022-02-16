package net.moddingplayground.thematic.mixin;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChestBlockEntity.class)
public interface ChestBlockEntityAccessor {
    @Mutable @Accessor ViewerCountManager getStateManager();
    @Mutable @Accessor void setStateManager(ViewerCountManager manager);
}
