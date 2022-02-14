package net.moddingplayground.thematic.impl.datagen;

import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.ThemeDataToymaker;

import static net.moddingplayground.thematic.impl.item.ThematicItems.*;

public class ItemModelGenerator extends AbstractItemModelGenerator {
    public ItemModelGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        this.add(ANCIENT_ROPE, OVERGROWN_ANCHOR, OXIDIZED_COG);

        for (Theme theme : Thematic.THEME_REGISTRY) {
            for (Decoratable decoratable : Thematic.DECORATABLE_REGISTRY) {
                if (decoratable.getData(theme) instanceof ThemeDataToymaker toymaker) {
                    toymaker.generateItemModels(this);
                }
            }
        }
    }
}
