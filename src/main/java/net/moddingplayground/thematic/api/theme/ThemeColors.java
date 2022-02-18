package net.moddingplayground.thematic.api.theme;

import java.util.function.Supplier;

/**
 * Colors for use in text related to {@linkplain Theme themes}.
 */
public record ThemeColors(Supplier<Integer> light, Supplier<Integer> dark) {
    public ThemeColors(int light, int dark) {
        this(() -> light, () -> dark);
    }

    public int getLight() {
        return this.light.get();
    }

    public int getDark() {
        return this.dark.get();
    }
}
