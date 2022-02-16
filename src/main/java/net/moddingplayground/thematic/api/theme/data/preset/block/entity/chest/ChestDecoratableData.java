package net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest;

import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.block.theme.chest.ThemedChestBlock;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.BlockEntityDecoratableData;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.block.ParticleOnlyModelGen.*;

public class ChestDecoratableData<C extends ChestBlockEntity> extends BlockEntityDecoratableData<C> {
    private final Supplier<C> blockEntityForRender;
    private final Consumer<FabricBlockSettings> modifier;
    private final Supplier<ChestTextureStore> provider;
    private final Block particle;
    private final boolean wooden;

    public ChestDecoratableData(Theme theme, FabricBlockEntityTypeBuilder.Factory<C> type, Block particle, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, type, null);

        this.block = Suppliers.memoize(() -> new ThemedChestBlock(theme, this.acceptModifier(FabricBlockSettings.copyOf(Blocks.CHEST))));
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

    public ChestDecoratableData(Theme theme, FabricBlockEntityTypeBuilder.Factory<C> type, Block particle, Consumer<FabricBlockSettings> modifier) {
        this(theme, type, particle, modifier, true);
    }

    public ChestDecoratableData(Theme theme, FabricBlockEntityTypeBuilder.Factory<C> type, Block particle, boolean wooden) {
        this(theme, type, particle, s -> {}, wooden);
    }

    public ChestDecoratableData(Theme theme, FabricBlockEntityTypeBuilder.Factory<C> type, Block particle) {
        this(theme, type, particle, s -> {});
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

    protected ChestTextureStore createTextureProvider() {
        return new ChestTextureStore();
    }

    @Environment(EnvType.CLIENT)
    public ChestTextureStore getTextureProvider() {
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
        C blockEntity = this.getBlockEntityForRender();
        renderer.renderEntity(blockEntity, matrices, vertices, light, overlay);
    }

    @Environment(EnvType.CLIENT)
    protected List<Identifier> createAtlasTextures() {
        List<Identifier> list = Lists.newArrayList();
        Theme theme = this.getTheme();
        for (ChestType type : ChestType.values()) {
            ChestTextureStore provider = this.createTextureProvider();
            list.add(provider.getTexture(new ChestTextureContext(theme, type, false)));
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
        gen.add(item, i -> InheritingModelGen.inherit(gen.name(Blocks.CHEST.asItem(), "item/%s")));
    }

    @Override
    public void generateBlockTags(AbstractTagGenerator<Block> gen) {
        Block block = this.getBlock();
        gen.add(this.isWooden() ? BlockTags.AXE_MINEABLE : BlockTags.PICKAXE_MINEABLE, block);
    }

    public static class ChestTextureContext {
        private final Theme theme;
        private final ChestType chestType;
        private final boolean trapped;

        public ChestTextureContext(Theme theme, ChestType chestType, boolean trapped) {
            this.theme = theme;
            this.chestType = chestType;
            this.trapped = trapped;
        }

        public Theme getTheme() {
            return this.theme;
        }

        public ChestType getChestType() {
            return this.chestType;
        }

        public boolean isTrapped() {
            return this.trapped;
        }
    }

    public static class ChestTextureStore {
        protected final Function<ChestTextureContext, Identifier> textures;
        protected final Function<ChestTextureContext, SpriteIdentifier> sprites;

        public ChestTextureStore() {
            this.textures = Util.memoize(this::createTexture);
            this.sprites = Util.memoize(this::createSpriteIdentifier);
        }

        public Identifier createTexture(ChestTextureContext context) {
            Theme theme = context.getTheme();
            return theme.formatId(this.createTextureFormat(context));
        }

        protected String createTextureFormat(ChestTextureContext context) {
            ChestType type = context.getChestType();
            String stype = type == ChestType.SINGLE ? "" : "_" + type.asString();
            String strapped = context.isTrapped() ? "_trapped": "";
            return "%s/entity/chest/%%s".formatted(Thematic.MOD_ID) + strapped + stype;
        }

        protected SpriteIdentifier createSpriteIdentifier(ChestTextureContext context) {
            Identifier id = this.getTexture(context);
            return new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, id);
        }

        public Identifier getTexture(ChestTextureContext context) {
            return this.textures.apply(context);
        }

        public SpriteIdentifier getSpriteIdentifier(ChestTextureContext context) {
            return this.sprites.apply(context);
        }
    }
}
