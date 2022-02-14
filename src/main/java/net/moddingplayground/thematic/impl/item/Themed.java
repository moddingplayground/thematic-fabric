package net.moddingplayground.thematic.impl.item;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.List;

public interface Themed {
    Theme getTheme();

    default Text colorText(Text text, boolean title) {
        Theme theme = this.getTheme();
        Theme.Colors colors = theme.getColors();
        Style style = Style.EMPTY.withColor(title ? colors.getTitle() : colors.getDescription());
        return text.shallowCopy().fillStyle(style);
    }

    default Text colorText(Text text) {
        return colorText(text, true);
    }

    default void addColoredTooltip(List<Text> tooltip) {
        Theme theme = this.getTheme();
        String key = theme.getTranslationKey();
        Text text = this.colorText(new TranslatableText(key), false);
        tooltip.add(new TranslatableText("text.%s.theme".formatted(Thematic.MOD_ID), text).formatted(Formatting.GRAY));
    }
}
