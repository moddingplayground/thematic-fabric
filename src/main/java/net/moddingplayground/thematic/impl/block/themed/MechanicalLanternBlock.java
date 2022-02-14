package net.moddingplayground.thematic.impl.block.themed;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class MechanicalLanternBlock extends LanternBlock {
    public static final VoxelShape SHAPE = createCuboidShape(4.5, 0, 4.5, 11.5, 7, 11.5);
    public static final VoxelShape SHAPE_HANGING = createCuboidShape(4.5, 3, 4.5, 11.5, 10, 11.5);

    public MechanicalLanternBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return state.get(HANGING) ? SHAPE_HANGING : SHAPE;
    }
}
