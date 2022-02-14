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

    default Text colorText(Text text) {
        Theme theme = this.getTheme();
        Style style = Style.EMPTY.withColor(theme.getTooltipColor());
        return text.shallowCopy().fillStyle(style);
    }

    default void addColoredTooltip(List<Text> tooltip) {
        Theme theme = this.getTheme();
        Text themeText = this.colorText(new TranslatableText(theme.getTranslationKey()));
        tooltip.add(new TranslatableText("text.%s.theme".formatted(Thematic.MOD_ID), themeText).formatted(Formatting.GRAY));
    }
}
