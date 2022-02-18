package net.moddingplayground.thematic.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.moddingplayground.thematic.api.recipe.ThematicRecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    /**
     * Removes log warning spam by immediately defining the {@linkplain ThematicRecipeType#THEMING theming recipe type's}
     * group as {@link RecipeBookGroup#UNKNOWN}.
     */
    @Inject(method = "getGroupForRecipe", at = @At("HEAD"), cancellable = true)
    private static void onGetGroupForRecipe(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        RecipeType<?> recipeType = recipe.getType();
        if (recipeType == ThematicRecipeType.THEMING) cir.setReturnValue(RecipeBookGroup.UNKNOWN);
    }
}
