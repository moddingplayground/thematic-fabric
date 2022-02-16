package net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.moddingplayground.thematic.api.block.entity.theme.ThemedChestBlockEntity;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.function.Consumer;

public class ThemedChestDecoratableData extends ChestDecoratableData<ThemedChestBlockEntity> {
    public ThemedChestDecoratableData(Theme theme, Block particle, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, (pos, state) -> new ThemedChestBlockEntity(pos, state) {
            @Override
            public Theme getTheme() {
                return theme;
            }
        }, particle, modifier, wooden);
    }
}
