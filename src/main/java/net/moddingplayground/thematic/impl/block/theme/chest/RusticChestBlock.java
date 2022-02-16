package net.moddingplayground.thematic.impl.block.theme.chest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.moddingplayground.thematic.api.block.theme.chest.ThemedChestBlock;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest.ChestDecoratableData;
import net.moddingplayground.thematic.impl.block.ThematicProperties;
import net.moddingplayground.thematic.impl.theme.data.block.entity.chest.RusticChestDecoratableData;

public class RusticChestBlock extends ThemedChestBlock {
    public static final BooleanProperty TREASURE = ThematicProperties.TREASURE;

    public RusticChestBlock(Theme theme, Settings settings) {
        super(theme, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(TREASURE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(TREASURE));
    }

    @Override
    protected ChestDecoratableData.ChestTextureContext createTextureContext(Theme theme, BlockEntity blockEntity, ChestType type, boolean christmas) {
        return new RusticChestDecoratableData.RusticChestTextureContext(theme, type, false, blockEntity.getCachedState().get(TREASURE));
    }
}
