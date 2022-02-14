package net.moddingplayground.thematic.api.theme;

import java.util.function.Supplier;

public class ThemeColors {
    private final Supplier<Integer> title, description;

    private ThemeColors(Supplier<Integer> title, Supplier<Integer> description) {
        this.title = title;
        this.description = description;
    }

    public static ThemeColors of(Supplier<Integer> title, Supplier<Integer> description) {
        return new ThemeColors(title, description);
    }

    public static ThemeColors of(int title, int description) {
        return of(() -> title, () -> description);
    }

    public int getTitle() {
        return this.title.get();
    }

    public int getDescription() {
        return this.description.get();
    }
}
