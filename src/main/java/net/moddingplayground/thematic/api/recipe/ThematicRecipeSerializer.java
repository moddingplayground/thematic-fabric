package net.moddingplayground.thematic.api.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

public interface ThematicRecipeSerializer {
    RecipeSerializer<ThemingRecipe> THEMING = register("theming", new ThemingRecipe.Serializer<>(ThemingRecipe::new));

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Thematic.MOD_ID, id), serializer);
    }
}
