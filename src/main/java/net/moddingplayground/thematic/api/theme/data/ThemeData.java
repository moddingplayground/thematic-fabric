package net.moddingplayground.thematic.api.theme.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.moddingplayground.thematic.api.theme.Decoratable;

public interface ThemeData {
    void register(Decoratable decoratable);
    @Environment(EnvType.CLIENT) void registerClient(Decoratable decoratable);
}
