package net.moddingplayground.thematic.api.theme.data.preset;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.item.ThemedBlockItem;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.function.Supplier;

public class BlockEntityDecoratableData<T extends BlockEntity> extends BlockItemDecoratableData {
    private final Supplier<BlockEntityType<T>> blockEntityType;

    public BlockEntityDecoratableData(Theme theme, BlockEntityFactory<T> type, BlockFactory<T> block, ItemFactory item) {
        super(theme, null, item);
        this.block = Suppliers.memoize(() -> block.create(this::getBlockEntityType));
        this.blockEntityType = Suppliers.memoize(() -> type.create(this.getBlock()));
    }

    public BlockEntityDecoratableData(Theme theme, BlockEntityFactory<T> type, BlockFactory<T> block) {
        this(theme, type, block, ThemedBlockItem::new);
    }

    public BlockEntityDecoratableData(Theme theme, FabricBlockEntityTypeBuilder.Factory<T> type, BlockFactory<T> block) {
        this(theme, b -> FabricBlockEntityTypeBuilder.create(type, b).build(), block, ThemedBlockItem::new);
    }

    public BlockEntityType<T> getBlockEntityType() {
        return this.blockEntityType.get();
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> BlockEntityType<T> getBlockEntityType(Theme theme, Decoratable decoratable) {
        return decoratable.getData(theme, BlockEntityDecoratableData.class)
                          .orElseThrow()
                          .getBlockEntityType();
    }

    @Override
    public void register(Decoratable decoratable) {
        super.register(decoratable);
        Identifier id = this.createId(decoratable);

        BlockEntityType<T> type = this.getBlockEntityType();
        if (Registry.BLOCK_ENTITY_TYPE.getId(type) == null) Registry.register(Registry.BLOCK_ENTITY_TYPE, id, type);
    }

    @FunctionalInterface public interface BlockFactory<T extends BlockEntity> { Block create(Supplier<BlockEntityType<? extends T>> type); }
    @FunctionalInterface public interface BlockEntityFactory<T extends BlockEntity> { BlockEntityType<T> create(Block block); }
}
