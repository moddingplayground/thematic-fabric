package net.moddingplayground.thematic.impl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.impl.block.ThematicBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

@Environment(EnvType.CLIENT)
public class ThematicClientImpl implements Thematic, ClientModInitializer {
    private static ThematicClientImpl instance = null;
    protected final Logger logger;

    public ThematicClientImpl() {
        this.logger = LoggerFactory.getLogger("%s-client".formatted(MOD_ID));
        instance = this;
    }

    @Override
    public void onInitializeClient() {
        logger.info("Initializing {}-client", MOD_NAME);

        BlockRenderLayerMap renderLayers = BlockRenderLayerMap.INSTANCE;
        ThematicBlocks.forEach((theme, decoratable, block) -> {
            if (decoratable == LADDER || decoratable == LANTERN) renderLayers.putBlock(block, RenderLayer.getCutout());
        });
    }

    public static ThematicClientImpl getInstance() {
        return instance;
    }
}
