package net.moddingplayground.thematic.api.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.screen.DecoratorsTableScreenHandler;
import net.moddingplayground.thematic.mixin.client.ItemRendererInvoker;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class DecoratorsTableScreen extends HandledScreen<DecoratorsTableScreenHandler> {
    public static final Identifier TEXTURE = new Identifier(Thematic.MOD_ID, "textures/gui/decorators_table.png");

    private final MinecraftClient client = MinecraftClient.getInstance();
    private int spin;

    public DecoratorsTableScreen(DecoratorsTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void handledScreenTick() {
        this.spin++;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.drawBackground(matrices, delta, mouseX, mouseY);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (!this.handler.themeSlot.hasStack()) this.drawTexture(matrices, x + 26, y + 52, this.backgroundWidth, 0, 16, 16);

        ItemStack result = this.handler.resultSlot.getStack();
        this.renderResult(result, x + 80, y + 34);
    }

    public void renderResult(ItemStack stack, int x, int y) {
        if (stack.isEmpty()) return;

        TextureManager textureManager = this.client.getTextureManager();
        textureManager.getTexture(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).setFilter(false, false);
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        MatrixStack matrices = RenderSystem.getModelViewStack();
        matrices.push();

        matrices.translate(x, y, 100.0f + this.getZOffset());
        matrices.translate(8.0, 8.0, 0.0);
        matrices.scale(1.0f, -1.0f, 1.0f);
        matrices.scale(16.0f, 16.0f, 16.0f);

        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.disableGuiDepthLighting();

        VertexConsumerProvider.Immediate immediate = this.client.getBufferBuilders().getEntityVertexConsumers();
        BakedModel model = this.getBlockOrItemModel(stack, this.client.world, this.client.player, 0);
        this.renderResult(stack, new MatrixStack(), immediate, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, model);
        immediate.draw();

        RenderSystem.enableDepthTest();
        DiffuseLighting.enableGuiDepthLighting();
        RenderSystem.applyModelViewMatrix();

        matrices.pop();
    }

    public void renderResult(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay, BakedModel model) {
        matrices.push();
        matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(this.spin));
        matrices.translate(-0.5D, -0.5D, -0.5D);

        if (model.isBuiltin()) {
            BuiltinModelItemRenderer renderer = this.createBuiltinModelItemRenderer();
            renderer.render(stack, ModelTransformation.Mode.GUI, matrices, vertices, light, overlay);
        } else {
            RenderLayer layer = RenderLayers.getItemLayer(stack, true);
            VertexConsumer vertex = ItemRenderer.getDirectItemGlintConsumer(vertices, layer, true, stack.hasGlint());
            ((ItemRendererInvoker) this.client.getItemRenderer()).invokeRenderBakedItemModel(model, stack, light, overlay, matrices, vertex);
        }

        matrices.pop();
    }

    public BakedModel getBlockOrItemModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity, int seed) {
        BakedModel ret = this.client.getItemRenderer().getModel(stack, world, entity, seed);
        if (stack.getItem() instanceof BlockItem blockItem && !ret.isBuiltin()) {
            Block block = blockItem.getBlock();
            return this.client.getBlockRenderManager().getModel(block.getDefaultState());
        }
        return ret;
    }

    public BuiltinModelItemRenderer createBuiltinModelItemRenderer() {
        return  new BuiltinModelItemRenderer(this.client.getBlockEntityRenderDispatcher(), this.client.getEntityModelLoader());
    }
}
