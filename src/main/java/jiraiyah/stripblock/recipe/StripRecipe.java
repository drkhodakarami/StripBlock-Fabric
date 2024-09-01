package jiraiyah.stripblock.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

// Big shout and thanks to "ZeroNoRyouki" for helping me on finding proper solution for custom recipe/serializer
public class StripRecipe implements Recipe<SingleStackRecipeInput>
{
    protected final CraftingRecipeCategory category;
    protected final String group;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final float experience;
    protected final int processTime;

    public StripRecipe(String group, CraftingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int processTime)
    {
        this.category = category;
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world)
    {
        if(world.isClient)
            return false;
        return this.ingredient.test(input.item());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup)
    {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup)
    {
        return this.result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients()
    {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.ingredient);
        return defaultedList;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.WOOD_STRIP_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.WOOD_STRIP_TYPE;
    }

    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    public interface RecipeFactory<T extends StripRecipe> {
        T create(String group, CraftingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int processTime);
    }
}