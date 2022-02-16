package net.moddingplayground.thematic.api.theme.data.preset.bannerpattern;

import com.google.common.base.Suppliers;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPatterns;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;

import java.util.function.Supplier;

public class BannerPatternDecoratableData implements DecoratableData {
    private final Theme theme;
    private final Supplier<FrameBannerPattern> pattern;

    public BannerPatternDecoratableData(Theme theme, Supplier<FrameBannerPattern> pattern) {
        this.theme = theme;
        this.pattern = Suppliers.memoize(pattern::get);
    }

    public BannerPatternDecoratableData(Theme theme, boolean special) {
        this(theme, () -> new FrameBannerPattern(special));
    }

    public BannerPatternDecoratableData(Theme theme) {
        this(theme, FrameBannerPattern::new);
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    public FrameBannerPattern getPattern() {
        return this.pattern.get();
    }

    public static FrameBannerPattern getPattern(Theme theme, Decoratable decoratable) {
        return decoratable.getData(theme, BannerPatternDecoratableData.class)
                          .orElseThrow()
                          .getPattern();
    }

    @Override
    public void register(Decoratable decoratable) {
        Identifier id = this.createId(decoratable);
        FrameBannerPattern pattern = this.getPattern();
        Registry.register(FrameBannerPatterns.REGISTRY, id, pattern);
    }
}
