package net.moddingplayground.thematic.impl.data;

import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.moddingplayground.frame.api.toymaker.v0.generator.recipe.AbstractRecipeGenerator;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.tag.ThematicItemTags;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

import static net.moddingplayground.thematic.api.block.ThematicBlocks.*;

public class RecipeGenerator extends AbstractRecipeGenerator {
    public RecipeGenerator(String modId) {
        super(modId);
    }

    @Override
    public void generate() {
        this.add("decorators_table", ShapedRecipeJsonBuilder.create(DECORATORS_TABLE, 1)
                                                            .input('#', ItemTags.PLANKS)
                                                            .input('@', ThematicItemTags.DYES)
                                                            .pattern("@@")
                                                            .pattern("##")
                                                            .pattern("##")
                                                            .criterion("has_plank", hasItems(ItemTags.PLANKS))
                                                            .criterion("has_dye", hasItems(ThematicItemTags.DYES)));

        this.add("seat", ShapedRecipeJsonBuilder.create(SEAT, 3)
                                                            .input('#', ItemTags.WOOL)
                                                            .input('@', ItemTags.WOODEN_FENCES)
                                                            .pattern("###")
                                                            .pattern("@ @")
                                                            .criterion("has_wool", hasItems(ItemTags.WOOL))
                                                            .criterion("has_fence", hasItems(ItemTags.WOODEN_FENCES)));

        this.add("gate", ShapedRecipeJsonBuilder.create(GATE, 3)
                                                            .input('#', ItemTags.PLANKS)
                                                            .input('/', Items.IRON_NUGGET)
                                                            .pattern("# ")
                                                            .pattern("#/")
                                                            .pattern("# ")
                                                            .criterion("has_planks", hasItems(ItemTags.PLANKS))
                                                            .criterion("has_nugget", hasItem(Items.IRON_NUGGET)));

        for (Theme theme : ThematicRegistry.THEME) {
            for (Decoratable decoratable : ThematicRegistry.DECORATABLE) {
                decoratable.getData(theme, DecoratableDataToymaker.class).ifPresent(t -> t.generateRecipes(this));
            }
        }
    }
}
