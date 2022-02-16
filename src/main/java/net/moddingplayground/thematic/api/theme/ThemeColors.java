package net.moddingplayground.thematic.api.theme;

import java.util.function.Supplier;

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
