package net.moddingplayground.thematic.api.block.theme.chest;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.moddingplayground.frame.api.rendering.v0.ChestTextureProvider;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.Themed;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.BlockEntityDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest.ChestDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest.TrappedChestDecoratableData;

import java.util.function.BiPredicate;

public class TrappedThemedChestBlock extends TrappedChestBlock implements Themed, ChestTextureProvider {
    private final Theme theme;

    public TrappedThemedChestBlock(Theme theme, Settings settings) {
        super(settings);
        this.theme = theme;
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> fallback = ignoreBlocked ? (w, p) -> false : ChestBlock::isChestBlocked;
        return DoubleBlockProperties.toPropertySource(this.getExpectedEntityType(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world, pos, fallback);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BlockEntityType<? extends ChestBlockEntity> getExpectedEntityType() {
        Theme theme = this.getTheme();
        BlockEntityType<?> type = BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.TRAPPED_CHEST);
        return (BlockEntityType<? extends ChestBlockEntity>) type;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return this.getExpectedEntityType().instantiate(pos, state);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public SpriteIdentifier getSpriteIdentifier(BlockEntity blockEntity, ChestType type, boolean christmas) {
        Theme theme = this.getTheme();
        TrappedChestDecoratableData<?> data = BuiltinDecoratables.TRAPPED_CHEST.getData(theme, TrappedChestDecoratableData.class).orElseThrow();
        ChestDecoratableData.ChestTextureStore provider = data.getTextureProvider();
        return provider.getSpriteIdentifier(this.createTextureContext(theme, blockEntity, type, christmas));
    }

    @Environment(EnvType.CLIENT)
    protected ChestDecoratableData.ChestTextureContext createTextureContext(Theme theme, BlockEntity blockEntity, ChestType type, boolean christmas) {
        return new ChestDecoratableData.ChestTextureContext(theme, type, true);
    }
}
