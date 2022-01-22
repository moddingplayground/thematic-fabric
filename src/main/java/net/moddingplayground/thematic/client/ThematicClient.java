package net.moddingplayground.thematic.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.moddingplayground.thematic.block.ThematicBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.moddingplayground.thematic.Thematic.*;
import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

@Environment(EnvType.CLIENT)
public class ThematicClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("%s-client".formatted(MOD_ID));

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing {}-client", MOD_NAME);

        BlockRenderLayerMap renderLayers = BlockRenderLayerMap.INSTANCE;
        ThematicBlocks.forEach((theme, decoratable, block) -> {
            if (decoratable == LADDER || decoratable == LANTERN) renderLayers.putBlock(block, RenderLayer.getCutout());
        });

        LOGGER.info("Initialized {}-client", MOD_NAME);
    }
}
