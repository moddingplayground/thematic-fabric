package net.moddingplayground.thematic.api.theme;

import com.google.common.base.Suppliers;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.util.Themed;

import java.util.function.Supplier;

/**
 * A theme is an object representing a specific block set.
 */
public class Theme {
    private final Supplier<Item> item;
    private final ThemeColors colors;

    private final Supplier<Identifier> id;
    private final Supplier<Identifier> lootTableId;
    private final Supplier<String> translationKey;

    public Theme(Supplier<Item> item, ThemeColors colors) {
        this.item = item;
        this.colors = colors;

        this.id = Suppliers.memoize(() -> ThematicRegistry.THEME.getId(this));
        this.lootTableId = Suppliers.memoize(this::createLootTableId);
        this.translationKey = Suppliers.memoize(this::createTranslationKey);
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

    public Identifier getLootTableId() {
        return this.lootTableId.get();
    }

    protected Identifier createLootTableId() {
        Identifier id = this.getId();
        return new Identifier(Thematic.MOD_ID, "theme/%s/%s".formatted(id.getNamespace(), id.getPath()));
    }

    public String getTranslationKey() {
        return this.translationKey.get();
    }

    protected String createTranslationKey() {
        return "%s.theme.%s".formatted(Thematic.MOD_ID, this.getId());
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
        return item instanceof Themed themed && themed.getTheme() == theme;
    }

    public void toPacket(PacketByteBuf buf) {
        buf.writeString(this.toString());
    }

    public static Theme fromPacket(PacketByteBuf buf) {
        String theme = buf.readString();
        return ThematicRegistry.THEME.get(new Identifier(theme));
    }

    public JsonElement toJson() {
        return new JsonPrimitive(this.toString());
    }

    public boolean matches(ItemStack stack) {
        return stack.getItem() instanceof Themed themed && themed.getTheme() == this;
    }

    @Override
    public String toString() {
        return ThematicRegistry.THEME.getId(this).toString();
    }
}
