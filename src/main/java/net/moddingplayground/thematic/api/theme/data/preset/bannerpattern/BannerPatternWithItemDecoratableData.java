package net.moddingplayground.thematic.api.theme.data.preset.bannerpattern;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.generator.recipe.AbstractRecipeGenerator;
import net.moddingplayground.thematic.api.item.ThematicItemGroups;
import net.moddingplayground.thematic.api.item.ThemedFrameBannerPatternItem;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import java.util.function.Function;
import java.util.function.Supplier;

public class BannerPatternWithItemDecoratableData extends BannerPatternDecoratableData implements DecoratableDataToymaker {
    private final String id;
    private final Supplier<Item> item;

    public BannerPatternWithItemDecoratableData(Theme theme, String id, ItemFactory item, Supplier<FrameBannerPattern> pattern) {
        super(theme, pattern);
        this.id = id;
        this.item = Suppliers.memoize(() -> item.create(theme, this.getPattern(), new FabricItemSettings().group(ThematicItemGroups.THEMES).maxCount(1)));
    }

    public BannerPatternWithItemDecoratableData(Theme theme, String id, ItemFactory item) {
        this(theme, id, item, () -> new FrameBannerPattern(true));
    }

    public BannerPatternWithItemDecoratableData(Theme theme, String id) {
        this(theme, id, ThemedFrameBannerPatternItem::new);
    }

    public static Function<Theme, DecoratableData> create(String id) {
        return theme -> new BannerPatternWithItemDecoratableData(theme, id);
    }

    public String getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item.get();
    }

    @Override
    public void register(Decoratable decoratable) {
        super.register(decoratable);

        Identifier id = this.createItemId();
        Registry.register(Registry.ITEM, id, this.getItem());
    }

    public Identifier createItemId() {
        Theme theme = this.getTheme();
        Identifier id = theme.getId();
        return new Identifier(id.getNamespace(), this.getId().formatted(id.getPath()));
    }

    @Override
    public void generateItemModels(AbstractItemModelGenerator gen) {
        Item item = this.getItem();
        gen.add(item);
    }

    @Override
    public void generateRecipes(AbstractRecipeGenerator gen) {
        Theme theme = this.getTheme();
        Identifier id = theme.getId();
        Item themeItem = theme.getItem();
        Item item = this.getItem();
        Identifier itemId = this.createItemId();
        gen.add("%s/%s".formatted(id.getPath(), itemId.getPath()), gen.shapeless(themeItem, Items.PAPER, item, 1));
    }

    @FunctionalInterface public interface ItemFactory { Item create(Theme theme, FrameBannerPattern pattern, Item.Settings settings); }
}
