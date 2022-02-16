package net.moddingplayground.thematic.impl.theme.data.block.entity.chest;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest.ChestDecoratableData;
import net.moddingplayground.thematic.impl.block.entity.theme.chest.MetalChestBlockEntity;

import java.util.function.Consumer;

public class MetalChestDecoratableData extends ChestDecoratableData<MetalChestBlockEntity> {
    public MetalChestDecoratableData(Theme theme, Block particle, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, (pos, state) -> new MetalChestBlockEntity(pos, state) {
            @Override
            public Theme getTheme() {
                return theme;
            }
        }, particle, modifier, wooden);
    }
}
