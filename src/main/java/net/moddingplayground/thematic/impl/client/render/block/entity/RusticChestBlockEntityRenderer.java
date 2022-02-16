package net.moddingplayground.thematic.impl.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.moddingplayground.thematic.api.client.render.block.entity.ThematicChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.block.ThematicProperties;
import net.moddingplayground.thematic.impl.client.model.ThematicEntityModelLayers;

@Environment(EnvType.CLIENT)
public class RusticChestBlockEntityRenderer extends ThematicChestBlockEntityRenderer<ChestBlockEntity> {
    public RusticChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx, ThematicEntityModelLayers.RUSTIC_CHEST, ThematicEntityModelLayers.RUSTIC_DOUBLE_CHEST_LEFT, ThematicEntityModelLayers.RUSTIC_DOUBLE_CHEST_RIGHT);
    }

    @Override
    public float getOpenFactor(DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> property, ChestBlockEntity entity, float tickDelta) {
        BlockState state = entity.getCachedState();
        float ret = super.getOpenFactor(property, entity, tickDelta);
        return state.get(ThematicProperties.TREASURE) ? Math.max(0.1F, ret) : ret;
    }

    public static TexturedModelData getSingleTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData base = root.addChild(
            BASE,
            ModelPartBuilder.create()
                            .uv(0, 25)
                            .cuboid(-7.0F, -9.0F, -7.0F, 14.0F, 9.0F, 14.0F),
            ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        ModelPartData lid = root.addChild(
            LID,
            ModelPartBuilder.create()
                            .uv(0, 5)
                            .cuboid(-7.0F, -6.0F, -14.0F, 14.0F, 6.0F, 14.0F),
            ModelTransform.pivot(0.0F, 16.0F, 7.0F)
        );

        ModelPartData latch = lid.addChild(
            LATCH,
            ModelPartBuilder.create()
                            .uv(0, 28)
                            .cuboid(-2.0F, -11.0F, -8.0F, 4.0F, 3.0F, 1.0F)
                            .uv(1, 32)
                            .cuboid(-1.0F, -8.0F, -8.0F, 2.0F, 1.0F, 1.0F),
            ModelTransform.pivot(0.0F, 8.0F, -7.0F)
        );

        return TexturedModelData.of(data, 64, 48);
    }

    public static TexturedModelData getDoubleLeftTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData base = root.addChild(
            BASE,
            ModelPartBuilder.create()
                            .uv(0, 25)
                            .cuboid(-8.0F, -9.0F, -7.0F, 15.0F, 9.0F, 14.0F),
            ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        ModelPartData lid = root.addChild(
            LID,
            ModelPartBuilder.create()
                            .uv(0, 5)
                            .cuboid(0.0F, -6.0F, -14.0F, 15.0F, 6.0F, 14.0F),
            ModelTransform.pivot(-8.0F, 16.0F, 7.0F)
        );

        ModelPartData latch = lid.addChild(
            LATCH,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-8.0F, -11.0F, -8.0F, 2.0F, 3.0F, 1.0F)
                            .uv(0, 5)
                            .cuboid(-8.0F, -8.0F, -8.0F, 1.0F, 1.0F, 1.0F),
            ModelTransform.pivot(8.0F, 8.0F, -7.0F)
        );

        return TexturedModelData.of(data, 64, 48);
    }

    public static TexturedModelData getDoubleRightTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData base = root.addChild(
            BASE,
            ModelPartBuilder.create()
                            .uv(0, 25)
                            .cuboid(-7.0F, -9.0F, -7.0F, 15.0F, 9.0F, 14.0F),
            ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        ModelPartData lid = root.addChild(
            LID,
            ModelPartBuilder.create()
                            .uv(0, 5)
                            .cuboid(-15.0F, -6.0F, -14.0F, 15.0F, 6.0F, 14.0F),
            ModelTransform.pivot(8.0F, 16.0F, 7.0F)
        );

        ModelPartData latch = lid.addChild(
            LATCH,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(6.0F, -11.0F, -8.0F, 2.0F, 3.0F, 1.0F)
                            .uv(0, 5)
                            .cuboid(7.0F, -8.0F, -8.0F, 1.0F, 1.0F, 1.0F),
            ModelTransform.pivot(-8.0F, 8.0F, -7.0F)
        );

        return TexturedModelData.of(data, 64, 48);
    }
}
