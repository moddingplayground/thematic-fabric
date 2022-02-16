package net.moddingplayground.thematic.api;

import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.impl.theme.BuiltinDecoratablesImpl;

public interface BuiltinDecoratables {
    Decoratable BANNER_PATTERN = BuiltinDecoratablesImpl.BANNER_PATTERN;
    Decoratable LANTERN = BuiltinDecoratablesImpl.LANTERN;
    Decoratable LADDER = BuiltinDecoratablesImpl.LADDER;
    Decoratable BOOKSHELF = BuiltinDecoratablesImpl.BOOKSHELF;
    Decoratable CHEST = BuiltinDecoratablesImpl.CHEST;
    Decoratable TRAPPED_CHEST = BuiltinDecoratablesImpl.TRAPPED_CHEST;
}
