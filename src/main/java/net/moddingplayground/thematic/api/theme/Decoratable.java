package net.moddingplayground.thematic.api.theme;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.theme.data.DecoratableData;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * A decoratable is an object that holds information regarding
 * the registering of objects in relation to each {@link Theme}.
 */
public class Decoratable {
    private final Map<Theme, DecoratableData> themes;
    private final String format;

    public Decoratable(String format) {
        this.format = format;
        this.themes = Maps.newLinkedHashMap();
    }

    public String getFormat() {
        return this.format;
    }

    public <T> Optional<T> getData(Theme theme, Class<T> clazz) {
        return Optional.ofNullable(this.themes.get(theme)).map(clazz::cast);
    }

    public Optional<DecoratableData> getData(Theme theme) {
        return this.getData(theme, DecoratableData.class);
    }

    public Decoratable add(Theme theme, Function<Theme, DecoratableData> data) {
        if (this.themes.containsKey(theme)) throw new IllegalArgumentException("Theme " + theme + "already registered to " + this);
        this.themes.put(theme, data.apply(theme));
        return this;
    }

    public void register() {
        for (DecoratableData data : this.themes.values()) data.register(this);
    }

    @Environment(EnvType.CLIENT)
    public void registerClient() {
        for (DecoratableData data : this.themes.values()) data.registerClient(this);
    }

    @Override
    public String toString() {
        return ThematicRegistry.DECORATABLE.getId(this).toString();
    }
}
