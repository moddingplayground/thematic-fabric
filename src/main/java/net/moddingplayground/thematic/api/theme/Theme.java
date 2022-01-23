package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.item.ThemeItem;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum Theme {
    RUSTIC("ancient_rope", () -> 0x8B6C52, false),
    SUNKEN("overgrown_anchor", () -> 0x575F71, true),
    MECHANICAL("oxidized_cog", () -> 0x4C9484, true);

    private static final Theme[] THEMES = Theme.values();

    private final Supplier<Integer> tooltipColor;
    private final boolean metallic;

    private final String id, translationKey;
    private final Item item;
    private final ItemGroup itemGroup;

    Theme(String item, Supplier<Integer> tooltipColor, boolean metallic) {
        this.tooltipColor = tooltipColor;
        this.metallic = metallic;

        this.id = this.name().toLowerCase();
        this.translationKey = "%s.theme.%s".formatted(Thematic.MOD_ID, this.id);

        this.item = Registry.register(
            Registry.ITEM, new Identifier(Thematic.MOD_ID, item),
            new ThemeItem(this, new FabricItemSettings().maxCount(1).group(Thematic.ITEM_GROUP))
        );
        this.itemGroup = FabricItemGroupBuilder.build(
            new Identifier(Thematic.MOD_ID, "theme_%s".formatted(this.getId())),
            () -> new ItemStack(this.getItem())
        );
    }

    public String getId() {
        return this.id;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public Item getItem() {
        return this.item;
    }

    public int getTooltipColor() {
        return this.tooltipColor.get();
    }

    public boolean isMetallic() {
        return this.metallic;
    }

    public ItemGroup getItemGroup() {
        return this.itemGroup;
    }

    public Block get(Decoratable decoratable) {
        return Registry.BLOCK.get(Thematic.defaultedId(decoratable.format(this)));
    }

    public static void forEach(Consumer<Theme> action) {
        for (Theme theme : THEMES) action.accept(theme);
    }
}
