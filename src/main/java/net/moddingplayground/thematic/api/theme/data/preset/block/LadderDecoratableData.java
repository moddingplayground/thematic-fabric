package net.moddingplayground.thematic.api.theme.data.preset.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.impl.block.vanilla.PublicLadderBlock;

import java.util.function.Consumer;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen.*;

public class LadderDecoratableData extends BlockItemDecoratableData {
    private final boolean wooden;

    public LadderDecoratableData(Theme theme, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, () -> createLadder(modifier));
        this.wooden = wooden;
    }

    public LadderDecoratableData(Theme theme) {
        this(theme, s -> {}, true);
    }

    public boolean isWooden() {
        return this.wooden;
    }

    @Override
    public void register(Decoratable decoratable) {
        super.register(decoratable);
        Block block = this.getBlock();

        if (this.isWooden()) {
            FuelRegistry fuel = FuelRegistry.INSTANCE;
            fuel.add(block, 300);
        }

        StateRegistry.LADDERS.add(block);
        StateRegistry.LADDERS_DEATH_MESSAGES.add(block);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerClient(Decoratable decoratable) {
        Block block = this.getBlock();
        BlockRenderLayerMap renderLayers = BlockRenderLayerMap.INSTANCE;
        renderLayers.putBlock(block, RenderLayer.getCutout());
    }

    @Override
    public void generateStateModels(AbstractStateModelGenerator gen) {
        Block block = this.getBlock();
        gen.add(block, b -> gen.facingHorizontalRotated(gen.name(b), ladder(gen.name(b))));
    }

    @Override
    public void generateItemModels(AbstractItemModelGenerator gen) {
        Item item = this.getItem();
        gen.add(item, gen::generatedBlock);
    }

    @Override
    public void generateBlockTags(AbstractTagGenerator<Block> gen) {
        Block block = this.getBlock();
        gen.add(BlockTags.CLIMBABLE, block);
        gen.add(this.isWooden() ? BlockTags.AXE_MINEABLE : BlockTags.PICKAXE_MINEABLE, block);
    }

    public static Block createLadder(Consumer<FabricBlockSettings> modifier) {
        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.LADDER);
        modifier.accept(settings);
        return new PublicLadderBlock(settings);
    }
}
