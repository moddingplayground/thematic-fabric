package net.moddingplayground.thematic.api.theme.data.preset.bannerpattern;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;
import net.moddingplayground.thematic.api.util.AccessibleMemoizeFunction;

public class BannerPatternDecoratableData implements DecoratableData {
    private final Theme theme;
    private final AccessibleMemoizeFunction<Identifier, BannerPattern> pattern;

    public BannerPatternDecoratableData(Theme theme, BannerPatternFactory pattern) {
        this.theme = theme;
        this.pattern = new AccessibleMemoizeFunction<>(pattern::create);
    }

    public BannerPatternDecoratableData(Theme theme) {
        this(theme, id -> new BannerPattern(id.toString()));
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    public BannerPattern getPattern() {
        if (this.pattern.isEmpty()) throw new IllegalStateException("Pattern must be present!");
        return this.pattern.getFirst();
    }

    public BannerPattern getPattern(Identifier id) {
        return this.pattern.apply(id);
    }

    public BannerPattern getPattern(Decoratable decoratable) {
        return this.getPattern(this.createId(decoratable));
    }

    public static BannerPattern getPattern(Theme theme, Decoratable decoratable) {
        return decoratable.getData(theme, BannerPatternDecoratableData.class)
                          .orElseThrow()
                          .getPattern(decoratable);
    }

    @Override
    public void register(Decoratable decoratable) {
        Identifier id = this.createId(decoratable);
        BannerPattern pattern = this.getPattern(id);
        Registry.register(Registry.BANNER_PATTERN, id, pattern);
    }

    @FunctionalInterface public interface BannerPatternFactory { BannerPattern create(Identifier id); }
}
