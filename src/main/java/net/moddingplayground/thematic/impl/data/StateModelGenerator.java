package net.moddingplayground.thematic.impl.data;

import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import static net.moddingplayground.frame.api.toymaker.v0.generator.model.InheritingModelGen.*;
import static net.moddingplayground.thematic.impl.block.ThematicBlocks.*;

public class StateModelGenerator extends AbstractStateModelGenerator {
    public StateModelGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        this.add(DECORATORS_TABLE, b -> simple(name(b), cube(
            name(b, "block/%s_front"),
            name(b, "block/%s_east"),
            name(b, "block/%s_front"),
            name(b, "block/%s_west"),
            name(b, "block/%s_top"),
            name(b, "block/%s_bottom")
        )));

        for (Theme theme : Thematic.THEME_REGISTRY) {
            for (Decoratable decoratable : Thematic.DECORATABLE_REGISTRY) {
                decoratable.getData(theme, DecoratableDataToymaker.class).ifPresent(t -> t.generateStateModels(this));
            }
        }
    }
}
