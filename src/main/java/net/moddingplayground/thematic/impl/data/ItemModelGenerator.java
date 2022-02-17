package net.moddingplayground.thematic.impl.data;

import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import static net.moddingplayground.thematic.impl.block.ThematicBlocks.*;
import static net.moddingplayground.thematic.impl.item.ThematicItems.*;

public class ItemModelGenerator extends AbstractItemModelGenerator {
    public ItemModelGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        this.block(DECORATORS_TABLE);
        this.add(ANCIENT_ROPE, OVERGROWN_ANCHOR, OXIDIZED_COG);

        for (Theme theme : Thematic.THEME_REGISTRY) {
            for (Decoratable decoratable : Thematic.DECORATABLE_REGISTRY) {
                decoratable.getData(theme, DecoratableDataToymaker.class).ifPresent(t -> t.generateItemModels(this));
            }
        }
    }
}
