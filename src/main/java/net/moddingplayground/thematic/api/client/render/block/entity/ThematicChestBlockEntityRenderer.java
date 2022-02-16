package net.moddingplayground.thematic.api.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Calendar;

/**
 * <p>A renderer, representing the 3 models of any {@link ChestBlockEntity}: single,
 * double left, and double right.</p>
 *
 * <p>This is a slightly modified version of the vanilla {@link ChestBlockEntityRenderer},
 * parenting the latch to the lid for simplicity.</p>
 *
 * <div class="fabric">
 * <table border=1>
 * <caption>Model parts of this model</caption>
 * <tr>
 *   <th>Part Name</th><th>Parent</th><th>Corresponding Fields</th>
 * </tr>
 * <tr>
 *   <td>{@value BASE}</td><td>Root part</td><td>{@link #singleBase}, {@link #doubleLeftBase}, {@link #doubleRightBase}</td>
 * </tr>
 * <tr>
 *   <td>{@value LID}</td><td>Root part</td><td>{@link #singleLid}, {@link #doubleLeftLid}, {@link #doubleRightLid}</td>
 * </tr>
 * <tr>
 *   <td>{@value LATCH}</td><td>{@value LID}</td><td>{@link #singleLatch}, {@link #doubleLeftLatch}, {@link #doubleRightLatch}</td>
 * </tr>
 * </table>
 * </div>
 */
@Environment(EnvType.CLIENT)
public class ThematicChestBlockEntityRenderer<T extends ChestBlockEntity> implements BlockEntityRenderer<T> {
    public static final String BASE = "base";
    public static final String LID = "lid";
    public static final String LATCH = "latch";

    protected final ModelPart singleBase, singleLid, singleLatch;
    protected final ModelPart doubleLeftBase, doubleLeftLid, doubleLeftLatch;
    protected final ModelPart doubleRightBase, doubleRightLid, doubleRightLatch;

    private boolean christmas = false;

    public ThematicChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx, EntityModelLayer single, EntityModelLayer left, EntityModelLayer right) {
        ModelPart rootSingle = ctx.getLayerModelPart(single);
        this.singleBase = rootSingle.getChild(BASE);
        this.singleLid = rootSingle.getChild(LID);
        this.singleLatch = this.singleLid.getChild(LATCH);
        ModelPart rootDoubleLeft = ctx.getLayerModelPart(left);
        this.doubleLeftBase = rootDoubleLeft.getChild(BASE);
        this.doubleLeftLid = rootDoubleLeft.getChild(LID);
        this.doubleLeftLatch = this.doubleLeftLid.getChild(LATCH);
        ModelPart rootDoubleRight = ctx.getLayerModelPart(right);
        this.doubleRightBase = rootDoubleRight.getChild(BASE);
        this.doubleRightLid = rootDoubleRight.getChild(LID);
        this.doubleRightLatch = this.doubleRightLid.getChild(LATCH);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (month == 12 - 1) {
            int date = calendar.get(Calendar.DATE);
            if (date >= 24 && date <= 26) this.christmas = true;
        }
    }

    public boolean isChristmas() {
        return this.christmas;
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        BlockState state = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType type = state.contains(ChestBlock.CHEST_TYPE) ? state.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        if (!(state.getBlock() instanceof AbstractChestBlock<?> chest)) return;

        matrices.push();

        matrices.translate(0.5D, 1.0D, 0.5D);

        float rotation = state.get(ChestBlock.FACING).asRotation();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-rotation));
        matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(180.0F));

        matrices.translate(0.0D, -0.5D, 0.0D);

        DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> property = world != null
            ? chest.getBlockEntitySource(state, world, entity.getPos(), true)
            : DoubleBlockProperties.PropertyRetriever::getFallback;
        float open = this.getOpenFactor(property, entity, tickDelta);
        int l = property.apply(new LightmapCoordinatesRetriever<>()).applyAsInt(light);

        SpriteIdentifier sprite = this.getTexture(entity, type, this.isChristmas());
        VertexConsumer vertex = sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        boolean single = type == ChestType.SINGLE;
        if (!single) {
            if (type == ChestType.LEFT) this.render(matrices, vertex, this.doubleLeftLid, this.doubleLeftBase, open, l, overlay);
            else this.render(matrices, vertex, this.doubleRightLid, this.doubleRightBase, open, l, overlay);
        } else this.render(matrices, vertex, this.singleLid, this.singleBase, open, l, overlay);

        matrices.pop();
    }

    public float getOpenFactor(DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> property, T entity, float tickDelta) {
        float open = property.apply(ChestBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
        open = 1.0f - open;
        open = 1.0f - (open * open * open);
        return open;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart base, float openFactor, int light, int overlay) {
        lid.pitch = (float) -(openFactor * Math.PI / 2);
        lid.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }

    public SpriteIdentifier getTexture(T entity, ChestType type, boolean christmas) {
        return TexturedRenderLayers.getChestTexture(entity, type, christmas);
    }
}
