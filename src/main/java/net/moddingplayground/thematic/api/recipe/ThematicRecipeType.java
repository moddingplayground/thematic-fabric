package net.moddingplayground.thematic.api.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

public interface ThematicRecipeType {
    RecipeType<ThemingRecipe> THEMING = register("theming");

    private static <T extends Recipe<?>> RecipeType<T> register(final String id) {
        Identifier identifier = new Identifier(Thematic.MOD_ID, id);
        return Registry.register(Registry.RECIPE_TYPE, identifier, new RecipeType<T>() {
            @Override
            public String toString() {
                return identifier.toString();
            }
        });
    }
}
