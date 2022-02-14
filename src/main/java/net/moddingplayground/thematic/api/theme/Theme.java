package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.impl.item.ThemeItem;
import net.moddingplayground.thematic.impl.item.Themed;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum Theme {
    RUSTIC("ancient_rope", Colors.of(0xA48365, 0x8B6C52), false),
    SUNKEN("overgrown_anchor", Colors.of(0x737E96, 0x575F71), true),
    MECHANICAL("oxidized_cog", Colors.of(0x6EC59F, 0x4C9484), true);

    private static final Theme[] THEMES = Theme.values();

    private final String id, translationKey;
    private final Colors colors;
    private final boolean metallic;
    private final Item item;

    Theme(String itemId, Colors colors, boolean metallic) {
        this.id = this.name().toLowerCase();
        this.translationKey = "%s.theme.%s".formatted(Thematic.MOD_ID, this.id);
        this.colors = colors;
        this.metallic = metallic;
        this.item = Registry.register(
            Registry.ITEM, new Identifier(Thematic.MOD_ID, itemId),
            new ThemeItem(this, new FabricItemSettings().maxCount(1))
        );
    }

    public String getId() {
        return this.id;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public Colors getColors() {
        return this.colors;
    }

    public boolean isMetallic() {
        return this.metallic;
    }

    public Item getItem() {
        return this.item;
    }

    public Block get(Decoratable decoratable) {
        return Registry.BLOCK.get(Thematic.defaultedId(decoratable.format(this)));
    }

    public static void forEach(Consumer<Theme> action) {
        for (Theme theme : THEMES) action.accept(theme);
    }

    public static boolean tabPredicate(Theme theme, Item item) {
        return item instanceof Themed themed && themed.getTheme() == theme && item != theme.getItem();
    }

    public static class Colors {
        private final Supplier<Integer> title, description;

        private Colors(Supplier<Integer> title, Supplier<Integer> description) {
            this.title = title;
            this.description = description;
        }

        public static Colors of(Supplier<Integer> title, Supplier<Integer> description) {
            return new Colors(title, description);
        }

        public static Colors of(int title, int description) {
            return of(() -> title, () -> description);
        }

        public int getTitle() {
            return this.title.get();
        }

        public int getDescription() {
            return this.description.get();
        }
    }
}
