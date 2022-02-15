package net.moddingplayground.thematic.api.theme.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.theme.Decoratable;

public interface DecoratableData {
    Identifier createId(Decoratable decoratable);

    void register(Decoratable decoratable);
    @Environment(EnvType.CLIENT) void registerClient(Decoratable decoratable);
}
