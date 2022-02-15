package net.moddingplayground.thematic.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.mixin.ItemAccessor;

public class ThemeItem extends Item implements Themed {
    private final Theme theme;

    public ThemeItem(Theme theme, Settings settings) {
        super(settings);
        this.theme = theme;
        ((ItemAccessor) this).setRecipeRemainder(this);
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    @Override
    public Text getName() {
        return this.colorText(super.getName());
    }

    @Override
    public Text getName(ItemStack stack) {
        return this.colorText(super.getName(stack));
    }
}
