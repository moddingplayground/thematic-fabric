package net.moddingplayground.thematic.impl.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.impl.client.render.block.entity.MechanicalChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.RusticChestBlockEntityRenderer;
import net.moddingplayground.thematic.impl.client.render.block.entity.SunkenChestBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public interface ThematicEntityModelLayers {
    EntityModelLayer RUSTIC_CHEST = main("rustic_chest", RusticChestBlockEntityRenderer::getSingleTexturedModelData);
    EntityModelLayer RUSTIC_DOUBLE_CHEST_LEFT = main("rustic_double_chest_left", RusticChestBlockEntityRenderer::getLeftTexturedModelData);
    EntityModelLayer RUSTIC_DOUBLE_CHEST_RIGHT = main("rustic_double_chest_right", RusticChestBlockEntityRenderer::getRightTexturedModelData);

    EntityModelLayer SUNKEN_CHEST = main("sunken_chest", SunkenChestBlockEntityRenderer::getSingleTexturedModelData);
    EntityModelLayer SUNKEN_DOUBLE_CHEST_LEFT = main("sunken_double_chest_left", SunkenChestBlockEntityRenderer::getLeftTexturedModelData);
    EntityModelLayer SUNKEN_DOUBLE_CHEST_RIGHT = main("sunken_double_chest_right", SunkenChestBlockEntityRenderer::getRightTexturedModelData);

    EntityModelLayer MECHANICAL_CHEST = main("mechanical_chest", MechanicalChestBlockEntityRenderer::getSingleTexturedModelData);
    EntityModelLayer MECHANICAL_DOUBLE_CHEST_LEFT = main("mechanical_double_chest_left", MechanicalChestBlockEntityRenderer::getLeftTexturedModelData);
    EntityModelLayer MECHANICAL_DOUBLE_CHEST_RIGHT = main("mechanical_double_chest_right", MechanicalChestBlockEntityRenderer::getRightTexturedModelData);

    private static EntityModelLayer register(String id, String layer, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer ret = new EntityModelLayer(new Identifier(Thematic.MOD_ID, id), layer);
        EntityModelLayerRegistry.registerModelLayer(ret, provider);
        return ret;
    }

    private static EntityModelLayer main(String id, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        return register(id, "main", provider);
    }
}
