package net.moddingplayground.thematic.api;

import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

/**
 * An entrypoint for Thematic-related initialization.
 * In {@code fabric.mod.json}, the entrypoint is
 * defined with the {@value Thematic#MOD_ID} key.
 */
@FunctionalInterface
public interface ThematicEntrypoint {
    /**
     * Register instances of {@link Theme} and {@link Decoratable} during this entrypoint.
     */
    void onInitializeThematic();
}
