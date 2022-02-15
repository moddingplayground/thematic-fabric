package net.moddingplayground.thematic.api.block.theme.chest;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.moddingplayground.frame.api.rendering.v0.ChestTextureProvider;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.item.Themed;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.BlockEntityDecoratableData;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class ThemedChestBlock extends ChestBlock implements Themed, ChestTextureProvider {
    private final Theme theme;

    public ThemedChestBlock(Theme theme, Settings settings) {
        super(settings, null);
        this.theme = theme;
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BlockEntityType<? extends ChestBlockEntity> getExpectedEntityType() {
        Theme theme = this.getTheme();
        BlockEntityType<?> type = BlockEntityDecoratableData.getBlockEntityType(theme, BuiltinDecoratables.CHEST);
        return (BlockEntityType<? extends ChestBlockEntity>) type;
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> fallback = ignoreBlocked ? (w, p) -> false : ChestBlock::isChestBlocked;
        return DoubleBlockProperties.toPropertySource(this.getExpectedEntityType(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world, pos, fallback);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return this.getExpectedEntityType().instantiate(pos, state);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public SpriteIdentifier getSpriteIdentifier(BlockEntity blockEntity, ChestType type, boolean christmas) {
        return TextureStore.SPRITE.apply(this, type);
    }

    @Environment(EnvType.CLIENT)
    public static class TextureStore {
        public static final BiFunction<ThemedChestBlock, ChestType, SpriteIdentifier> SPRITE = Util.memoize((block, type) -> createSpriteIdentifier(block.getTheme(), type));

        public static Identifier createTexture(Theme theme, ChestType type) {
            String format = "%s/entity/chest/%%s".formatted(Thematic.MOD_ID);
            return type == ChestType.SINGLE
                ? theme.formatId(format)
                : theme.formatId("%s_double_%s".formatted(format, type.asString()));
        }

        @Environment(EnvType.CLIENT)
        private static SpriteIdentifier createSpriteIdentifier(Theme theme, ChestType type) {
            return new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, createTexture(theme, type));
        }
    }
}
