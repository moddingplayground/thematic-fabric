package net.moddingplayground.thematic.impl.block;

import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.moddingplayground.thematic.impl.screen.DecoratorsTableScreenHandler;
import net.moddingplayground.thematic.impl.stat.ThematicStats;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class DecoratorsTableBlock extends Block {
    public final Supplier<Text> title;

    public DecoratorsTableBlock(Settings settings) {
        super(settings);
        this.title = Suppliers.memoize(() -> new TranslatableText("container.%s".formatted(Registry.BLOCK.getId(this))));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        player.incrementStat(ThematicStats.INTERACT_WITH_DECORATORS_TABLE);
        return ActionResult.CONSUME;
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new DecoratorsTableScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), this.title.get());
    }
}
