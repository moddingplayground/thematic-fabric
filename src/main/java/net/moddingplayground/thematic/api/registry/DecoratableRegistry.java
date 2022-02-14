package net.moddingplayground.thematic.api.registry;

import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.impl.registry.DecoratableRegistryImpl;

public interface DecoratableRegistry extends Iterable<Decoratable> {
    DecoratableRegistry INSTANCE = new DecoratableRegistryImpl();
    Decoratable register(Decoratable decoratable);
}
