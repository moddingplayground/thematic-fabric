package net.moddingplayground.thematic.api.theme.data.preset.block.entity;

import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.block.entity.theme.TrappedThemedChestBlockEntity;
import net.moddingplayground.thematic.api.block.theme.chest.ThemedChestBlock;
import net.moddingplayground.thematic.api.block.theme.chest.TrappedThemedChestBlock;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.block.ParticleOnlyModelGen.*;

public class TrappedChestDecoratableData extends BlockEntityDecoratableData<TrappedChestBlockEntity> {
    private final Supplier<TrappedChestBlockEntity> blockEntityForRender;
    private final Consumer<FabricBlockSettings> modifier;
    private final Block particle;
    private final boolean wooden;

    public TrappedChestDecoratableData(Theme theme, Block particle, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, (pos, state) -> new TrappedThemedChestBlockEntity(pos, state) {
            @Override
            public Theme getTheme() {
                return theme;
            }
        }, null);

        this.block = Suppliers.memoize(() -> new TrappedThemedChestBlock(theme, this.acceptModifier(FabricBlockSettings.copyOf(Blocks.CHEST))));

        this.blockEntityForRender = Suppliers.memoize(() -> {
            Block block = this.getBlock();
            BlockEntityType<TrappedChestBlockEntity> type = this.getBlockEntityType();
            return type.instantiate(BlockPos.ORIGIN, block.getDefaultState());
        });
        this.modifier = modifier;
        this.particle = particle;
        this.wooden = wooden;
    }

    public TrappedChestDecoratableData(Theme theme, Block particle, Consumer<FabricBlockSettings> modifier) {
        this(theme, particle, modifier, true);
    }

    public TrappedChestDecoratableData(Theme theme, Block particle, boolean wooden) {
        this(theme, particle, s -> {}, wooden);
    }

    public TrappedChestDecoratableData(Theme theme, Block particle) {
        this(theme, particle, s -> {});
    }

    public TrappedChestBlockEntity getBlockEntityForRender() {
        return this.blockEntityForRender.get();
    }

    public Optional<Block> getParticle() {
        return Optional.ofNullable(this.particle);
    }

    public FabricBlockSettings acceptModifier(FabricBlockSettings settings) {
        this.modifier.accept(settings);
        return settings;
    }

    public boolean isWooden() {
        return this.wooden;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerClient(Decoratable decoratable) {
        Block block = this.getBlock();
        Theme theme = this.getTheme();

        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((texture, registry) -> {
            for (ChestType type : ChestType.values()) registry.register(ThemedChestBlock.TextureStore.createTexture(theme, type, true));
        });

        BuiltinItemRendererRegistry.INSTANCE.register(block, (stack, mode, matrices, vertices, light, overlay) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            BlockEntityRenderDispatcher renderer = client.getBlockEntityRenderDispatcher();
            TrappedChestBlockEntity blockEntity = this.getBlockEntityForRender();
            renderer.renderEntity(blockEntity, matrices, vertices, light, overlay);
        });
    }

    @Override
    public void generateStateModels(AbstractStateModelGenerator gen) {
        Block block = this.getBlock();
        Optional<Block> particle = this.getParticle();
        gen.add(block, b -> gen.simple(gen.name(b), particles(gen.name(particle.orElse(b)))));
    }

    @Override
    public void generateItemModels(AbstractItemModelGenerator gen) {
        Item item = this.getItem();
        gen.add(item, i -> InheritingModelGen.inherit(gen.name(Blocks.TRAPPED_CHEST.asItem(), "item/%s")));
    }

    @Override
    public void generateBlockTags(AbstractTagGenerator<Block> gen) {
        Block block = this.getBlock();
        gen.add(this.isWooden() ? BlockTags.AXE_MINEABLE : BlockTags.PICKAXE_MINEABLE, block);
    }
}
