package net.moddingplayground.thematic.impl;

import com.google.common.reflect.Reflection;
import net.moddingplayground.thematic.api.BuiltinDecoratables;
import net.moddingplayground.thematic.api.BuiltinThemes;
import net.moddingplayground.thematic.api.ThematicEntrypoint;

public final class ThematicMetaImpl implements ThematicEntrypoint {
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeThematic() {
        Reflection.initialize(BuiltinThemes.class, BuiltinDecoratables.class);
    }
}
