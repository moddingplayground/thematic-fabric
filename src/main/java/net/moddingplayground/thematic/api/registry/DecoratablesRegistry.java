package net.moddingplayground.thematic.api.registry;

import net.moddingplayground.thematic.api.theme.Decoratable;

import java.util.ArrayList;
import java.util.Iterator;

public final class DecoratablesRegistry implements Iterable<Decoratable> {
    public static final DecoratablesRegistry INSTANCE = new DecoratablesRegistry();

    private final ArrayList<Decoratable> entries = new ArrayList<>();

    private DecoratablesRegistry() {}

    public Decoratable register(Decoratable decoratable) {
        if (!this.entries.add(decoratable)) throw new IllegalArgumentException("The same decoratable was registered twice");
        return decoratable;
    }

    @Override
    public Iterator<Decoratable> iterator() {
        return this.entries.iterator();
    }
}
