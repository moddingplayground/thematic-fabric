package net.moddingplayground.thematic.api.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class GateBlock extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final EnumProperty<DoorHinge> HINGE = Properties.DOOR_HINGE;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public static final VoxelShape NORTH_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    public static final VoxelShape SOUTH_SHAPE = createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    public static final VoxelShape EAST_SHAPE = createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape WEST_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    public GateBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                                              .with(FACING, Direction.NORTH)
                                              .with(OPEN, false)
                                              .with(HINGE, DoorHinge.LEFT)
                                              .with(POWERED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        boolean closed = !state.get(OPEN);
        boolean right = state.get(HINGE) == DoorHinge.RIGHT;
        return switch (facing) {
            default    -> closed ?  WEST_SHAPE : (right ? SOUTH_SHAPE : NORTH_SHAPE );
            case SOUTH -> closed ? NORTH_SHAPE : (right ?  WEST_SHAPE :  EAST_SHAPE );
            case WEST  -> closed ?  EAST_SHAPE : (right ? NORTH_SHAPE : SOUTH_SHAPE );
            case NORTH -> closed ? SOUTH_SHAPE : (right ?  EAST_SHAPE :  WEST_SHAPE );
        };
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return type != NavigationType.WATER && state.get(OPEN);
    }

    public int getCloseSoundEventId() {
        return this.material == Material.METAL ? WorldEvents.IRON_TRAPDOOR_CLOSES : WorldEvents.WOODEN_TRAPDOOR_CLOSES;
    }

    public int getOpenSoundEventId() {
        return this.material == Material.METAL ? WorldEvents.IRON_TRAPDOOR_OPENS : WorldEvents.WOODEN_TRAPDOOR_OPENS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        boolean powered = world.isReceivingRedstonePower(pos);

        BlockState out = this.getDefaultState()
                             .with(HINGE, this.getHinge(ctx))
                             .with(POWERED, powered)
                             .with(OPEN, powered);

        BlockPos posd = pos.down();
        BlockState stated = world.getBlockState(posd);
        return stated.contains(FACING)
            ? out.with(FACING, stated.get(FACING))
            : out.with(FACING, ctx.getPlayerFacing());

    }

    public DoorHinge getHinge(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction facing = ctx.getPlayerFacing();

        BlockPos posd = pos.down();
        BlockState stated = world.getBlockState(posd);
        if (stated.contains(HINGE)) return stated.get(HINGE);

        BlockPos posu = pos.up();
        Direction facingycc = facing.rotateYCounterclockwise();
        BlockPos posycc = pos.offset(facingycc);
        BlockState stateycc = world.getBlockState(posycc);
        BlockPos posuycc = posu.offset(facingycc);
        BlockState stateuycc = world.getBlockState(posuycc);
        Direction facingyc = facing.rotateYClockwise();
        BlockPos posyc = pos.offset(facingyc);
        BlockState stateyc = world.getBlockState(posyc);
        BlockPos posuyc = posu.offset(facingyc);
        BlockState stateuyc = world.getBlockState(posuyc);

        int i = (stateycc.isFullCube(world, posycc) ? -1 : 0)
            + (stateuycc.isFullCube(world, posuycc) ? -1 : 0)
            + (stateyc.isFullCube(world, posyc)     ?  1 : 0)
            + (stateuyc.isFullCube(world, posuyc)   ?  1 : 0);
        boolean isycc = stateycc.isOf(this);
        boolean isyc = stateyc.isOf(this);
        if (isycc && !isyc || i > 0) return DoorHinge.RIGHT;
        if (isyc && !isycc || i < 0) return DoorHinge.LEFT;

        int ox = facing.getOffsetX();
        int oz = facing.getOffsetZ();
        Vec3d hit = ctx.getHitPos();
        double x = hit.x - (double)pos.getX();
        double z = hit.z - (double)pos.getZ();
        return ox < 0 && z < 0.5 || ox > 0 && z > 0.5 || oz < 0 && x > 0.5 || oz > 0 && x < 0.5
            ? DoorHinge.RIGHT
            : DoorHinge.LEFT;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (this.material == Material.METAL) return ActionResult.PASS;
        state = state.cycle(OPEN);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
        world.syncWorldEvent(player, state.get(OPEN) ? this.getOpenSoundEventId() : this.getCloseSoundEventId(), pos, 0);
        world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        this.synchronizeDoor(world, pos, world.getBlockState(pos));
        return ActionResult.success(world.isClient);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos);
        if (!this.getDefaultState().isOf(block) && powered != state.get(POWERED)) {
            if (powered != state.get(OPEN)) {
                this.playOpenCloseSound(world, pos, powered);
                world.emitGameEvent(powered ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            }
            world.setBlockState(pos, state.with(POWERED, powered).with(OPEN, powered), Block.NOTIFY_LISTENERS);
            this.synchronizeDoor(world, pos, world.getBlockState(pos));
        }
    }

    public void synchronizeDoor(World world, BlockPos pos, BlockState state) {
        BlockPos posd = pos.down();
        BlockState stated = world.getBlockState(posd);
        if (stated.getBlock() instanceof DoorBlock
            && stated.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER
            && stated.get(FACING)         == state.get(FACING)
            && stated.get(HINGE)          == state.get(HINGE)
        ) world.setBlockState(posd, stated.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS);
    }

    public void playOpenCloseSound(World world, BlockPos pos, boolean open) {
        world.syncWorldEvent(null, open ? this.getOpenSoundEventId() : this.getCloseSoundEventId(), pos, 0);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return mirror == BlockMirror.NONE ? state : state.rotate(mirror.getRotation(state.get(FACING))).cycle(HINGE);
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        return pos.hashCode();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, HINGE, POWERED);
    }

    public boolean isOpen(BlockState state) {
        return state.get(OPEN);
    }

    public static boolean isWooden(World world, BlockPos pos) {
        return GateBlock.isWooden(world.getBlockState(pos));
    }

    public static boolean isWooden(BlockState state) {
        return state.getBlock() instanceof GateBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
    }
}
