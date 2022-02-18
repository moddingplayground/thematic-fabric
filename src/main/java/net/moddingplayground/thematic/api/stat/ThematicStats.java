package net.moddingplayground.thematic.api.stat;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

public interface ThematicStats {
    Identifier INTERACT_WITH_DECORATORS_TABLE = register("interact_with_decorators_table", StatFormatter.DEFAULT);

    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(Thematic.MOD_ID, id);
        Registry.register(Registry.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
}
