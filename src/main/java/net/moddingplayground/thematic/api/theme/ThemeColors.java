package net.moddingplayground.thematic.api.theme;

import java.util.function.Supplier;

public record ThemeColors(Supplier<Integer> light, Supplier<Integer> dark) {
    public static ThemeColors of(Supplier<Integer> light, Supplier<Integer> dark) {
        return new ThemeColors(light, dark);
    }

    public static ThemeColors of(int light, int dark) {
        return of(() -> light, () -> dark);
    }

    public int getLight() {
        return this.light.get();
    }

    public int getDark() {
        return this.dark.get();
    }
}
