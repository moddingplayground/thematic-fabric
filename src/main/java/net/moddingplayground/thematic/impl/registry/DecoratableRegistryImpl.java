package net.moddingplayground.thematic.impl.registry;

import net.moddingplayground.thematic.api.registry.DecoratableRegistry;
import net.moddingplayground.thematic.api.theme.Decoratable;

import java.util.ArrayList;
import java.util.Iterator;

public final class DecoratableRegistryImpl implements DecoratableRegistry {
    private final ArrayList<Decoratable> entries;

    public DecoratableRegistryImpl() {
        this.entries = new ArrayList<>();
    }

    @Override
    public Decoratable register(Decoratable decoratable) {
        if (!this.entries.add(decoratable)) throw new IllegalArgumentException("The same decoratable was registered twice");
        return decoratable;
    }

    @Override
    public Iterator<Decoratable> iterator() {
        return this.entries.iterator();
    }
}
