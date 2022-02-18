package net.moddingplayground.thematic.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.moddingplayground.frame.api.blocks.v0.seat.DefaultSeatBlock;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ThematicSeatBlock extends DefaultSeatBlock implements Waterloggable {
    public static final VoxelShape SHAPE_DOUBLE_NORTH = createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    public static final VoxelShape SHAPE_DOUBLE_SOUTH = createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    public static final VoxelShape SHAPE_DOUBLE_WEST = createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    public static final VoxelShape SHAPE_DOUBLE_EAST = createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    public static final VoxelShape SHAPE_SINGLE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    public static final Vec3d SEATED_OFFSET = new Vec3d(0.5D, (9.0D / 16.0D) / 2, 0.5D);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<Type> TYPE = ThematicProperties.SEAT_TYPE;

    public ThematicSeatBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(TYPE, Type.SINGLE));
    }

    @Override
    public Vec3d getSeatedOffset(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return SEATED_OFFSET;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState nstate, WorldAccess world, BlockPos pos, BlockPos npos) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (nstate.isOf(this) && direction.getAxis().isHorizontal()) {
            Type chestType = nstate.get(TYPE);
            if (state.get(TYPE) == Type.SINGLE && chestType != Type.SINGLE && state.get(FACING) == nstate.get(FACING) && getFacing(nstate) == direction.getOpposite()) {
                return state.with(TYPE, chestType.getOpposite());
            }
        } else if (getFacing(state) == direction) return state.with(TYPE, Type.SINGLE);
        return super.getStateForNeighborUpdate(state, direction, nstate, world, pos, npos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction ndir;
        Type type = Type.SINGLE;
        Direction facing = ctx.getPlayerFacing().getOpposite();
        boolean cancel = ctx.shouldCancelInteraction();
        Direction side = ctx.getSide();
        if (side.getAxis().isHorizontal() && cancel && (ndir = this.getNeighborDirection(ctx, side.getOpposite())) != null && ndir.getAxis() != side.getAxis()) {
            facing = ndir;
            type = facing.rotateYCounterclockwise() == side.getOpposite() ? Type.RIGHT : Type.LEFT;
        }
        if (type == Type.SINGLE && !cancel) {
            if (facing == this.getNeighborDirection(ctx, facing.rotateYClockwise())) {
                type = Type.LEFT;
            } else if (facing == this.getNeighborDirection(ctx, facing.rotateYCounterclockwise())) type = Type.RIGHT;
        }
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, facing).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
        return super.getFluidState(state);
    }

    @Nullable
    private Direction getNeighborDirection(ItemPlacementContext ctx, Direction dir) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
        return state.isOf(this) && state.get(TYPE) == Type.SINGLE ? state.get(FACING) : null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == Type.SINGLE) return SHAPE_SINGLE;
        return switch (getFacing(state)) {
            default -> SHAPE_DOUBLE_NORTH;
            case SOUTH -> SHAPE_DOUBLE_SOUTH;
            case WEST -> SHAPE_DOUBLE_WEST;
            case EAST -> SHAPE_DOUBLE_EAST;
        };
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float distance) {
        super.onLandedUpon(world, state, pos, entity, distance * 0.5f);
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            this.bounceEntity(entity);
        }
    }

    public void bounceEntity(Entity entity) {
        Vec3d velocity = entity.getVelocity();
        if (velocity.y < 0.0) {
            double factor = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(velocity.x, -velocity.y * 0.66D * factor, velocity.z);
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, TYPE);
    }

    public static Direction getFacing(BlockState state) {
        Direction direction = state.get(FACING);
        return state.get(TYPE) == Type.LEFT ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    }

    public enum Type implements StringIdentifiable {
        SINGLE("single", 0),
        LEFT("left", 2),
        RIGHT("right", 1);

        private static final Type[] VALUES = Type.values();
        private final String name;
        private final int opposite;

        Type(String name, int opposite) {
            this.name = name;
            this.opposite = opposite;
        }

        public Type getOpposite() {
            return VALUES[this.opposite];
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
