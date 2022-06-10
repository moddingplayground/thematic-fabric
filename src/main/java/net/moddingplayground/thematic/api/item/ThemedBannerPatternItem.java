package net.moddingplayground.thematic.api.item;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.tag.TagKey;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.Themed;

public class ThemedBannerPatternItem extends BannerPatternItem implements Themed {
    private final Theme theme;

    public ThemedBannerPatternItem(Theme theme, TagKey<BannerPattern> pattern, Settings settings) {
        super(pattern, settings);
        this.theme = theme;
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }
}
