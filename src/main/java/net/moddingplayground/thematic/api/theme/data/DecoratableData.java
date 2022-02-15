package net.moddingplayground.thematic.api.theme.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;

public interface DecoratableData {
    Theme getTheme();

    default Identifier createId(Decoratable decoratable) {
        Theme theme = this.getTheme();
        Identifier id = theme.getId();
        return new Identifier(id.getNamespace(), theme.format(decoratable));
    }

    void register(Decoratable decoratable);
    @Environment(EnvType.CLIENT) default void registerClient(Decoratable decoratable) {}
}
