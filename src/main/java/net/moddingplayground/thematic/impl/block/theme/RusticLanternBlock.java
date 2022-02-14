package net.moddingplayground.thematic.impl.block.theme;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class RusticLanternBlock extends LanternBlock {
    public static final VoxelShape SHAPE = VoxelShapes.union(
        createCuboidShape(5, 0, 5, 11, 6, 11),
        createCuboidShape(4, 6, 4, 12, 8, 12),
        createCuboidShape(6, 8, 6, 10, 10, 10)
    );

    public static final VoxelShape SHAPE_HANGING = VoxelShapes.union(
        createCuboidShape(5, 1, 5, 11, 7, 11),
        createCuboidShape(4, 7, 4, 12, 9, 12),
        createCuboidShape(6, 9, 6, 10, 11, 10)
    );

    public RusticLanternBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return state.get(HANGING) ? SHAPE_HANGING : SHAPE;
    }
}
