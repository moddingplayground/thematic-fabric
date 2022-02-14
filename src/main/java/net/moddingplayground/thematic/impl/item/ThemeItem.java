package net.moddingplayground.thematic.impl.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.moddingplayground.thematic.api.theme.Theme;

public class ThemeItem extends Item implements Themed {
    private final Theme theme;

    public ThemeItem(Theme theme, Settings settings) {
        super(settings);
        this.theme = theme;
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
