package net.moddingplayground.thematic.api.theme;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.data.ThemeData;

import java.util.Map;
import java.util.function.Function;

public class Decoratable {
    private final Map<Theme, ThemeData> themes;
    private final String format;

    public Decoratable(String format) {
        this.format = format;
        this.themes = Maps.newLinkedHashMap();
    }

    public String getFormat() {
        return this.format;
    }

    public ThemeData getData(Theme theme) {
        return this.themes.get(theme);
    }

    public Decoratable add(Theme theme, Function<Theme, ThemeData> data) {
        if (this.themes.containsKey(theme)) throw new IllegalArgumentException("Theme " + theme + "already registered to " + this);
        this.themes.put(theme, data.apply(theme));
        return this;
    }

    public void register() {
        for (ThemeData data : this.themes.values()) data.register(this);
    }

    @Environment(EnvType.CLIENT)
    public void registerClient() {
        for (ThemeData data : this.themes.values()) data.registerClient(this);
    }

    @Override
    public String toString() {
        Identifier id = Thematic.DECORATABLE_REGISTRY.getId(this);
        return id == null ? "Unregistered Decoratable" : id.toString();
    }
}
