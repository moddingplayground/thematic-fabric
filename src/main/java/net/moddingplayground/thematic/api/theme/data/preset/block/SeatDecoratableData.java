package net.moddingplayground.thematic.api.theme.data.preset.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.BlockTags;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.block.ThematicBlocks;
import net.moddingplayground.thematic.api.block.ThematicSeatBlock;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.impl.data.StateModelGenerator;

import java.util.function.Consumer;

public class SeatDecoratableData extends BlockItemDecoratableData {
    private final boolean wooden;

    public SeatDecoratableData(Theme theme, Block base, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, () -> createSeat(base, modifier));
        this.wooden = wooden;
    }

    public SeatDecoratableData(Theme theme, Block base, Consumer<FabricBlockSettings> modifier) {
        this(theme, base, modifier, true);
    }

    public SeatDecoratableData(Theme theme, Block base, boolean wooden) {
        this(theme, base, s -> {}, wooden);
    }

    public SeatDecoratableData(Theme theme, Block base) {
        this(theme, base, true);
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
            FlammableBlockRegistry flammable = FlammableBlockRegistry.getDefaultInstance();
            flammable.add(block, 30, 20);
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
        gen.add(block, b -> StateModelGenerator.seat(gen, b));
    }

    @Override
    public void generateBlockTags(AbstractTagGenerator<Block> gen) {
        Block block = this.getBlock();
        gen.add(this.isWooden() ? BlockTags.AXE_MINEABLE : BlockTags.PICKAXE_MINEABLE, block);
    }

    @Override
    protected Ingredient getIngredient() {
        return Ingredient.ofItems(ThematicBlocks.SEAT);
    }

    public static Block createSeat(Block base, Consumer<FabricBlockSettings> modifier) {
        FabricBlockSettings settings = FabricBlockSettings.copyOf(base).nonOpaque();
        modifier.accept(settings);
        return new ThematicSeatBlock(settings);
    }
}
