package net.moddingplayground.thematic.api.data;

import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.recipe.ThematicRecipeSerializer;
import net.moddingplayground.thematic.api.recipe.ThematicRecipeType;
import net.moddingplayground.thematic.api.theme.Theme;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Allows for data generation of {@linkplain ThematicRecipeType#THEMING theming recipes}.
 */
public class ThemingRecipeJsonFactory implements CraftingRecipeJsonFactory {
    private final Theme theme;
    private final Ingredient input;
    private final Item output;
    private final int count;
    private final Advancement.Task builder = Advancement.Task.create();
    @Nullable private String group;
    private final RecipeSerializer<?> serializer;

    public ThemingRecipeJsonFactory(RecipeSerializer<?> serializer, Theme theme, Ingredient input, ItemConvertible output, int outputCount) {
        this.serializer = serializer;
        this.theme = theme;
        this.input = input;
        this.output = output.asItem();
        this.count = outputCount;
    }

    public ThemingRecipeJsonFactory(Theme theme, Ingredient input, ItemConvertible output, int outputCount) {
        this(ThematicRecipeSerializer.THEMING, theme, input, output, outputCount);
    }

    public ThemingRecipeJsonFactory(Theme theme, Ingredient input, ItemConvertible output) {
        this(theme, input, output, 1);
    }

    @Override
    public ThemingRecipeJsonFactory criterion(String string, CriterionConditions criterionConditions) {
        this.builder.criterion(string, criterionConditions);
        return this;
    }

    @Override
    public ThemingRecipeJsonFactory group(@Nullable String string) {
        this.group = string;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier id) {
        this.validate(id);
        this.builder.parent(new Identifier("recipes/root")).criterion("has_the_recipe", RecipeUnlockedCriterion.create(id)).rewards(AdvancementRewards.Builder.recipe(id)).criteriaMerger(CriterionMerger.OR);
        exporter.accept(new JsonProvider(id, this.serializer, this.group == null ? "" : this.group, this.theme, this.input, this.output, this.count, this.builder, new Identifier(id.getNamespace(), "recipes/" + this.output.getGroup().getName() + "/" + id.getPath())));
    }

    private void validate(Identifier recipeId) {
        if (this.builder.getCriteria().isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + recipeId);
    }

    public record JsonProvider(Identifier recipeId, RecipeSerializer<?> serializer, String group, Theme theme,
                               Ingredient input, Item output, int count, Advancement.Task builder,
                               Identifier advancementId) implements RecipeJsonProvider {

        @Override
        public void serialize(JsonObject json) {
            if (!this.group.isEmpty()) json.addProperty("group", this.group);
            json.add("theme", this.theme.toJson());
            json.add("ingredient", this.input.toJson());
            json.addProperty("result", Registry.ITEM.getId(this.output).toString());
            json.addProperty("count", this.count);
        }

        @Override
        public Identifier getRecipeId() {
            return this.recipeId;
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return this.serializer;
        }

        @Override
        @Nullable
        public JsonObject toAdvancementJson() {
            return this.builder.toJson();
        }

        @Override
        @Nullable
        public Identifier getAdvancementId() {
            return this.advancementId;
        }
    }
}
