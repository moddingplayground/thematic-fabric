package net.moddingplayground.thematic.impl.datagen;

import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.ThemeDataToymaker;

public class StateModelGenerator extends AbstractStateModelGenerator {
    public StateModelGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        for (Theme theme : Thematic.THEME_REGISTRY) {
            for (Decoratable decoratable : Thematic.DECORATABLE_REGISTRY) {
                if (decoratable.getData(theme) instanceof ThemeDataToymaker toymaker) {
                    toymaker.generateStateModels(this);
                }
            }
        }
    }
}
