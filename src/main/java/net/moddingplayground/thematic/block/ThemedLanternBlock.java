package net.moddingplayground.thematic.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.moddingplayground.thematic.api.theme.Theme;

public class ThemedLanternBlock extends LanternBlock {
    private final Theme theme;

    public ThemedLanternBlock(Theme theme, Settings settings) {
        super(settings);
        this.theme = theme;
    }

    public Theme getTheme() {
        return this.theme;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Theme.Data data = theme.getData();
        return state.get(HANGING) ? data.lanternShapeHanging() : data.lanternShape();
    }
}
