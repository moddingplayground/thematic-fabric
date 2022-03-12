package net.moddingplayground.thematic.impl.theme.data.block.entity.chest;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest.ChestDecoratableData;
import net.moddingplayground.thematic.impl.block.entity.theme.chest.MetalChestBlockEntity;

import java.util.function.Consumer;
import java.util.function.Function;

public class MetalChestDecoratableData extends ChestDecoratableData<MetalChestBlockEntity> {
    public MetalChestDecoratableData(Theme theme, Block particle, Consumer<FabricBlockSettings> modifier) {
        super(theme, (pos, state) -> new MetalChestBlockEntity(pos, state) {
            @Override
            public Theme getTheme() {
                return theme;
            }
        }, particle, modifier, false);
    }

    public static Function<Theme, DecoratableData> create(Block particle, Consumer<FabricBlockSettings> modifier) {
        return theme -> new MetalChestDecoratableData(theme, particle, modifier);
    }
}
