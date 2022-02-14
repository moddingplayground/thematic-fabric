package net.moddingplayground.thematic.impl.block.theme;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SunkenLanternBlock extends LanternBlock {
    public static final VoxelShape SHAPE = VoxelShapes.union(
        createCuboidShape(6, 9, 6, 10, 11, 10),
        createCuboidShape(5, 0, 5, 11, 9, 11)
    );

    public static final VoxelShape SHAPE_HANGING = VoxelShapes.union(
        createCuboidShape(6, 9, 6, 10, 11, 10),
        createCuboidShape(5, 0, 5, 11, 9, 11)
    );

    public SunkenLanternBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return state.get(HANGING) ? SHAPE_HANGING : SHAPE;
    }
}
