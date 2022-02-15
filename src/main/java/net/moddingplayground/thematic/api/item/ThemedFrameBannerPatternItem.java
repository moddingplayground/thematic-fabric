package net.moddingplayground.thematic.api.item;

import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPatternItem;
import net.moddingplayground.thematic.api.theme.Theme;

import java.util.function.Supplier;

public class ThemedFrameBannerPatternItem extends FrameBannerPatternItem implements Themed {
    private final Theme theme;

    public ThemedFrameBannerPatternItem(Theme theme, Supplier<FrameBannerPattern> pattern, Settings settings) {
        super(pattern, settings);
        this.theme = theme;
    }

    public ThemedFrameBannerPatternItem(Theme theme, FrameBannerPattern pattern, Settings settings) {
        super(pattern, settings);
        this.theme = theme;
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }
}
