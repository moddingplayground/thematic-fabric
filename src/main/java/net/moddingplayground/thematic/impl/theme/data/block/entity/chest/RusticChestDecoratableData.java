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
import net.moddingplayground.thematic.impl.block.entity.theme.RusticChestBlockEntity;
import net.moddingplayground.thematic.impl.block.theme.chest.RusticChestBlock;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.Consumer;

public class RusticChestDecoratableData extends ChestDecoratableData<RusticChestBlockEntity> {
    public RusticChestDecoratableData(Theme theme, Block particle, Consumer<FabricBlockSettings> modifier, boolean wooden) {
        super(theme, RusticChestBlockEntity::new, particle, modifier, wooden);
        this.block = Suppliers.memoize(() -> new RusticChestBlock(theme, this.acceptModifier(FabricBlockSettings.copyOf(Blocks.CHEST))));
    }

    public RusticChestDecoratableData(Theme theme, Block particle) {
        this(theme, particle, s -> {}, true);
    }

    @Override
    protected ChestTextureStore createTextureProvider() {
        return new RusticChestTextureStore();
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected List<Identifier> createAtlasTextures() {
        List<Identifier> list = Lists.newArrayList();
        Theme theme = this.getTheme();
        for (ChestType type : ChestType.values()) {
            RusticChestTextureStore provider = (RusticChestTextureStore) this.createTextureProvider();
            list.add(provider.getTexture(new RusticChestTextureContext(theme, type, false, false)));
            list.add(provider.getTexture(new RusticChestTextureContext(theme, type, false, true)));
        }
        return list;
    }

    public static class RusticChestTextureContext extends ChestTextureContext {
        private final boolean treasure;

        public RusticChestTextureContext(Theme theme, ChestType chestType, boolean trapped, boolean treasure) {
            super(theme, chestType, trapped);
            this.treasure = treasure;
        }

        public boolean hasTreasure() {
            return this.treasure;
        }
    }

    public static class RusticChestTextureStore extends ChestTextureStore {
        public RusticChestTextureStore() {}

        @Override
        protected String createTextureFormat(ChestTextureContext context) {
            return super.createTextureFormat(context) + (((RusticChestTextureContext) context).hasTreasure() ? "_treasure" : "");
        }
    }
}
