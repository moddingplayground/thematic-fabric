package net.moddingplayground.thematic.api.theme.data.preset.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.BlockTags;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.block.GateBlock;
import net.moddingplayground.thematic.api.block.ThematicBlocks;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;
import net.moddingplayground.thematic.impl.data.StateModelGenerator;

import java.util.function.Consumer;
import java.util.function.Function;

public class GateDecoratableData extends BlockItemDecoratableData {
    private final boolean wooden;

    public GateDecoratableData(Theme theme, Block base, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, () -> createGate(base, modifier));
        this.wooden = wooden;
    }

    public GateDecoratableData(Theme theme, Block base, Consumer<FabricBlockSettings> modifier) {
        this(theme, base, modifier, true);
    }

    public GateDecoratableData(Theme theme, Block base, boolean wooden) {
        this(theme, base, s -> {}, wooden);
    }

    public GateDecoratableData(Theme theme, Block base) {
        this(theme, base, true);
    }

    public static Function<Theme, DecoratableData> create(Block base) {
        return theme -> new GateDecoratableData(theme, base);
    }

    public static Function<Theme, DecoratableData> createMetal(Block base, Consumer<FabricBlockSettings> modifier) {
        return theme -> new GateDecoratableData(theme, base, modifier, false);
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
            fuel.add(block, 200);
        }
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
        gen.add(block, b -> StateModelGenerator.gate(gen, b));
    }

    @Override
    public void generateBlockTags(AbstractTagGenerator<Block> gen) {
        Block block = this.getBlock();
        gen.add(this.isWooden() ? BlockTags.AXE_MINEABLE : BlockTags.PICKAXE_MINEABLE, block);
    }

    @Override
    protected Ingredient getIngredient() {
        return Ingredient.ofItems(ThematicBlocks.GATE);
    }

    public static Block createGate(Block base, Consumer<FabricBlockSettings> modifier) {
        FabricBlockSettings settings = FabricBlockSettings.copyOf(base).nonOpaque();
        modifier.accept(settings);
        return new GateBlock(settings);
    }
}
