package net.moddingplayground.thematic.impl.datagen;

import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractBlockLootTableGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.ThemeDataToymaker;

public class BlockLootTableGenerator extends AbstractBlockLootTableGenerator {
    public BlockLootTableGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        for (Theme theme : Thematic.THEME_REGISTRY) {
            for (Decoratable decoratable : Thematic.DECORATABLE_REGISTRY) {
                if (decoratable.getData(theme) instanceof ThemeDataToymaker toymaker) {
                    toymaker.generateBlockLootTables(this);
                }
            }
        }
    }
}
