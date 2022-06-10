package net.moddingplayground.thematic.api.theme;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.moddingplayground.thematic.api.Thematic;

import java.util.List;

/**
 * Implementations represent an object containing a {@link Theme}.
 */
public interface Themed {
    Theme getTheme();

    default Text colorText(Text text, boolean light) {
        Theme theme = this.getTheme();
        ThemeColors colors = theme.getColors();
        Style style = Style.EMPTY.withColor(light ? colors.getLight() : colors.getDark());
        return text.copy().fillStyle(style);
    }

    default Text colorText(Text text) {
        return colorText(text, true);
    }

    default void addColoredTooltip(List<Text> tooltip) {
        Theme theme = this.getTheme();
        String key = theme.getTranslationKey();
        Text text = this.colorText(Text.translatable(key), false);
        tooltip.add(Text.translatable("text.%s.theme".formatted(Thematic.MOD_ID), text).formatted(Formatting.GRAY));
    }
}
