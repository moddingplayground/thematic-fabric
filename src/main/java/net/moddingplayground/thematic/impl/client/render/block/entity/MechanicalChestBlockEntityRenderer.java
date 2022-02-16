package net.moddingplayground.thematic.impl.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.moddingplayground.thematic.api.client.render.block.entity.ThematicChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.model.ThematicEntityModelLayers;

@Environment(EnvType.CLIENT)
public class MechanicalChestBlockEntityRenderer extends ThematicChestBlockEntityRenderer<ChestBlockEntity> {
    public MechanicalChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx, ThematicEntityModelLayers.MECHANICAL_CHEST, ThematicEntityModelLayers.MECHANICAL_DOUBLE_CHEST_LEFT, ThematicEntityModelLayers.MECHANICAL_DOUBLE_CHEST_RIGHT);
    }

    public static TexturedModelData getSingleTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData base = root.addChild(
            BASE,
            ModelPartBuilder.create()
                            .uv(0, 37)
                            .cuboid(-7.0F, -6.0F, -7.0F, 14.0F, 6.0F, 14.0F),
            ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        ModelPartData lid = root.addChild(
            LID,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-7.0F, -9.0F, -14.0F, 14.0F, 5.0F, 14.0F)
                            .uv(0, 7)
                            .cuboid(-7.0F, -4.0F, -14.0F, 0.0F, 4.0F, 14.0F)
                            .uv(0, 15)
                            .cuboid(7.0F, -4.0F, -14.0F, 0.0F, 4.0F, 14.0F)
                            .uv(0, 25)
                            .cuboid(-7.0F, -4.0F, -14.0F, 14.0F, 4.0F, 0.0F)
                            .uv(0, 33)
                            .cuboid(-7.0F, -4.0F, 0.0F, 14.0F, 4.0F, 0.0F),
            ModelTransform.of(0.0F, 19.0F, 7.0F, -1.0036F, 0.0F, 0.0F)
        );

        ModelPartData latch = lid.addChild(
            LATCH,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-2.0F, -3.0F, -15.0F, 4.0F, 4.0F, 1.0F),
            ModelTransform.NONE
        );

        return TexturedModelData.of(data, 64, 64);
    }

    public static TexturedModelData getDoubleLeftTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData base = root.addChild(
            BASE,
            ModelPartBuilder.create()
                            .uv(0, 37)
                            .cuboid(-8.0F, -6.0F, -7.0F, 15.0F, 6.0F, 14.0F),
            ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        ModelPartData lid = root.addChild(
            LID,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -9.0F, -14.0F, 15.0F, 5.0F, 14.0F)
                            .uv(0, 19)
                            .cuboid(0.0F, -4.0F, -14.0F, 15.0F, 4.0F, 14.0F),
            ModelTransform.pivot(-8.0F, 19.0F, 7.0F)
        );

        ModelPartData latch = lid.addChild(
            LATCH,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-10.0F, -3.0F, -15.0F, 4.0F, 4.0F, 1.0F),
            ModelTransform.pivot(16.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    public static TexturedModelData getDoubleRightTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData base = root.addChild(
            BASE,
            ModelPartBuilder.create()
                            .uv(0, 37)
                            .cuboid(-7.0F, -6.0F, -7.0F, 15.0F, 6.0F, 14.0F),
            ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        ModelPartData lid = root.addChild(
            LID,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-15.0F, -9.0F, -14.0F, 15.0F, 5.0F, 14.0F)
                            .uv(0, 19)
                            .cuboid(-15.0F, -4.0F, -14.0F, 15.0F, 4.0F, 14.0F),
            ModelTransform.pivot(8.0F, 19.0F, 7.0F)
        );

        ModelPartData latch = lid.addChild(
            LATCH,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-10.0F, -3.0F, -15.0F, 4.0F, 4.0F, 1.0F),
            ModelTransform.pivot(0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }
}
