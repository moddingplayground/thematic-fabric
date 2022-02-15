package net.moddingplayground.thematic.api.theme;

import com.google.common.base.Suppliers;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.item.Themed;

import java.util.Optional;
import java.util.function.Supplier;

public class Theme {
    private final Supplier<Item> item;
    private final ThemeColors colors;

    private final Supplier<Identifier> id;
    private final Supplier<String> translationKey;

    public Theme(Supplier<Item> item, ThemeColors colors) {
        this.item = item;
        this.colors = colors;

        this.id = Suppliers.memoize(() -> Thematic.THEME_REGISTRY.getId(this));
        this.translationKey = Suppliers.memoize(() -> "%s.theme.%s".formatted(Thematic.MOD_ID, this.getId()));
    }

    public Item getItem() {
        return this.item.get();
    }

    public ThemeColors getColors() {
        return this.colors;
    }

    public Identifier getId() {
        return this.id.get();
    }

    public String getTranslationKey() {
        return this.translationKey.get();
    }

    public String format(String format) {
        Identifier id =  this.getId();
        return format.formatted(id.getPath(), id.getNamespace());
    }

    public String format(Decoratable decoratable) {
        String format = decoratable.getFormat();
        return this.format(format);
    }

    public Identifier formatId(String format) {
        Identifier id =  this.getId();
        return new Identifier(id.getNamespace(), this.format(format));
    }

    public static boolean tabPredicate(Theme theme, Item item) {
        return item instanceof Themed themed && themed.getTheme() == theme && item != theme.getItem();
    }

    @Override
    public String toString() {
        Identifier id = Thematic.THEME_REGISTRY.getId(this);
        return Optional.ofNullable(id).map(Identifier::toString).orElse("Unregistered Theme");
    }
}
