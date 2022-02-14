package net.moddingplayground.thematic.api;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.impl.ThematicImpl;
import net.moddingplayground.thematic.impl.registry.ThematicRegistry;

public interface Thematic {
    String MOD_ID   = "thematic";
    String MOD_NAME = "Thematic";

    Registry<Theme>       THEME_REGISTRY       = ThematicRegistry.THEME;
    Registry<Decoratable> DECORATABLE_REGISTRY = ThematicRegistry.DECORATABLE;

    static ItemGroup getItemGroup() {
        return ThematicImpl.getInstance().getItemGroup();
    }

    static Identifier defaultedId(String id) {
        return id.indexOf(Identifier.NAMESPACE_SEPARATOR) != -1 ? new Identifier(id) : new Identifier(Thematic.MOD_ID, id);
    }
}
