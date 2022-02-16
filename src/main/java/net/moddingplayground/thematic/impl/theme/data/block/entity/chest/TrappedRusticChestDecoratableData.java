package net.moddingplayground.thematic.impl.theme.data.block.entity.chest;

import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.ChestType;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest.ChestDecoratableData;
import net.moddingplayground.thematic.api.theme.data.preset.block.entity.chest.TrappedChestDecoratableData;
import net.moddingplayground.thematic.impl.block.entity.theme.TrappedRusticChestBlockEntity;
import net.moddingplayground.thematic.impl.block.theme.chest.TrappedRusticChestBlock;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.Consumer;

public class TrappedRusticChestDecoratableData extends TrappedChestDecoratableData<TrappedRusticChestBlockEntity> {
    public TrappedRusticChestDecoratableData(Theme theme, Block particle, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, TrappedRusticChestBlockEntity::new, particle, modifier, wooden);
        this.block = Suppliers.memoize(() -> new TrappedRusticChestBlock(theme, this.acceptModifier(FabricBlockSettings.copyOf(Blocks.CHEST))));
    }

    public TrappedRusticChestDecoratableData(Theme theme, Block particle) {
        this(theme, particle, s -> {}, true);
    }

    @Override
    protected ChestDecoratableData.ChestTextureStore createTextureProvider() {
        return new RusticChestDecoratableData.RusticChestTextureStore();
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected List<Identifier> createAtlasTextures() {
        List<Identifier> list = Lists.newArrayList();
        Theme theme = this.getTheme();
        for (ChestType type : ChestType.values()) {
            RusticChestDecoratableData.RusticChestTextureStore provider = (RusticChestDecoratableData.RusticChestTextureStore) this.createTextureProvider();
            list.add(provider.getTexture(new RusticChestDecoratableData.RusticChestTextureContext(theme, type, true, false)));
            list.add(provider.getTexture(new RusticChestDecoratableData.RusticChestTextureContext(theme, type, true, true)));
        }
        return list;
    }
}
