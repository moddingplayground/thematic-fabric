package net.moddingplayground.thematic.impl.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.impl.block.ThematicBlocks;

public class ThemingRecipe implements Recipe<Inventory> {
    private final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;
    protected final Identifier id;
    protected final String group;
    protected final Theme theme;
    protected final Ingredient input;
    protected final ItemStack output;

    public ThemingRecipe(RecipeType<?> type, RecipeSerializer<?> serializer, Identifier id, String group, Theme theme, Ingredient input, ItemStack output) {
        this.type = type;
        this.serializer = serializer;
        this.id = id;
        this.group = group;
        this.theme = theme;
        this.input = input;
        this.output = output;
    }

    public ThemingRecipe(Identifier id, String group, Theme theme, Ingredient input, ItemStack output) {
        this(ThematicRecipeType.THEMING, ThematicRecipeSerializer.THEMING, id, group, theme, input, output);
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        ingredients.add(this.input);
        return ingredients;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        ItemStack ingredient = inventory.getStack(0);
        ItemStack themed = inventory.getStack(1);
        return this.input.test(ingredient) && this.theme.matches(themed);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ThematicBlocks.DECORATORS_TABLE);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.output.copy();
    }

    public record Serializer<T extends ThemingRecipe>(RecipeFactory<T> factory) implements RecipeSerializer<T> {
        @Override
        public T read(Identifier identifier, JsonObject json) {
            String group = JsonHelper.getString(json, "group", "");
            String rawTheme = JsonHelper.getString(json, "theme", "");
            String rawOutput = JsonHelper.getString(json, "result");
            int count = JsonHelper.getInt(json, "count");

            Theme theme = Thematic.THEME_REGISTRY.get(new Identifier(rawTheme));
            Ingredient input = JsonHelper.hasArray(json, "ingredient")
                ? Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"))
                : Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
            ItemStack output = new ItemStack(Registry.ITEM.get(new Identifier(rawOutput)), count);

            return this.factory.create(identifier, group, theme, input, output);
        }

        @Override
        public T read(Identifier identifier, PacketByteBuf buf) {
            String group = buf.readString();
            Theme theme = Theme.fromPacket(buf);
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return this.factory.create(identifier, group, theme, input, output);
        }

        @Override
        public void write(PacketByteBuf buf, T recipe) {
            buf.writeString(recipe.group);
            recipe.theme.toPacket(buf);
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }

        public interface RecipeFactory<T extends ThemingRecipe> { T create(Identifier id, String group, Theme theme, Ingredient input, ItemStack output); }
    }
}
