package net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest;

import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.block.theme.chest.TrappedThemedChestBlock;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.BlockEntityDecoratableData;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.block.ParticleOnlyModelGen.*;

public class TrappedChestDecoratableData<C extends TrappedChestBlockEntity> extends BlockEntityDecoratableData<C> {
    private final Supplier<C> blockEntityForRender;
    private final Consumer<FabricBlockSettings> modifier;
    private final Supplier<ChestDecoratableData.ChestTextureStore> provider;
    private final Block particle;
    private final boolean wooden;

    public TrappedChestDecoratableData(Theme theme, FabricBlockEntityTypeBuilder.Factory<C> type, Block particle, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, type, null);

        this.block = Suppliers.memoize(() -> new TrappedThemedChestBlock(theme, this.acceptModifier(FabricBlockSettings.copyOf(Blocks.CHEST))));
        this.blockEntityForRender = Suppliers.memoize(() -> {
            Block block = this.getBlock();
            BlockEntityType<C> t = this.getBlockEntityType();
            return t.instantiate(BlockPos.ORIGIN, block.getDefaultState());
        });

        this.provider = Suppliers.memoize(this::createTextureProvider);
        this.modifier = modifier;
        this.particle = particle;
        this.wooden = wooden;
    }

    public C getBlockEntityForRender() {
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

    protected ChestDecoratableData.ChestTextureStore createTextureProvider() {
        return new ChestDecoratableData.ChestTextureStore();
    }

    @Environment(EnvType.CLIENT)
    public ChestDecoratableData.ChestTextureStore getTextureProvider() {
        return this.provider.get();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerClient(Decoratable decoratable) {
        Block block = this.getBlock();
        BuiltinItemRendererRegistry.INSTANCE.register(block, this::renderItem);
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((texture, registry) -> {
            List<Identifier> textures = this.createAtlasTextures();
            textures.forEach(registry::register);
        });
    }

    @Environment(EnvType.CLIENT)
    public void renderItem(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        BlockEntityRenderDispatcher renderer = client.getBlockEntityRenderDispatcher();
        ChestBlockEntity blockEntity = this.getBlockEntityForRender();
        renderer.renderEntity(blockEntity, matrices, vertices, light, overlay);
    }

    @Environment(EnvType.CLIENT)
    protected List<Identifier> createAtlasTextures() {
        List<Identifier> list = Lists.newArrayList();
        Theme theme = this.getTheme();
        for (ChestType type : ChestType.values()) {
            ChestDecoratableData.ChestTextureStore provider = this.createTextureProvider();
            list.add(provider.getTexture(new ChestDecoratableData.ChestTextureContext(theme, type, true)));
        }
        return list;
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
        boolean wooden = this.isWooden();
        gen.add(wooden ? BlockTags.AXE_MINEABLE : BlockTags.PICKAXE_MINEABLE, block);
        gen.add(ConventionalBlockTags.CHESTS, block);
    }

    @Override
    protected Ingredient getIngredient() {
        return Ingredient.ofItems(Blocks.TRAPPED_CHEST);
    }
}
