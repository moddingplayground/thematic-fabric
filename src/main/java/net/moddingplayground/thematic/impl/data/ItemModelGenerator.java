package net.moddingplayground.thematic.impl.data;

import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import static net.moddingplayground.thematic.api.block.ThematicBlocks.*;
import static net.moddingplayground.thematic.api.item.ThematicItems.*;

public class ItemModelGenerator extends AbstractItemModelGenerator {
    public ItemModelGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        this.block(DECORATORS_TABLE, SEAT, GATE);
        this.add(ANCIENT_ROPE, OVERGROWN_ANCHOR, OXIDIZED_COG);

        for (Theme theme : ThematicRegistry.THEME) {
            for (Decoratable decoratable : ThematicRegistry.DECORATABLE) {
                decoratable.getData(theme, DecoratableDataToymaker.class).ifPresent(t -> t.generateItemModels(this));
            }
        }
    }
}
