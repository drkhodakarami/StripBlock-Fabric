package jiraiyah.wood_stripper.recipe;

import jiraiyah.jiralib.record.ConfiguredIngredient;
import jiraiyah.jiralib.record.OutputItemStackPayload;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StripRecipeBuilder implements CraftingRecipeJsonBuilder
{
    private final String group;
    private final RecipeCategory category;
    private final ConfiguredIngredient ingredient;
    private final ItemStack output;
    private final float experience;
    private final int processTime;

    private final Map<String, AdvancementCriterion<?>> criteria = new HashMap<>();

    public StripRecipeBuilder(String group, RecipeCategory category, ConfiguredIngredient ingredient, ItemStack output, float experience, int processTime)
    {
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.output = output;
        this.experience = experience;
        this.processTime = processTime;
    }

    @Override
    public CraftingRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion)
    {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group)
    {
        return this;
    }

    @Override
    public Item getOutputItem()
    {
        return this.output.getItem();
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey)
    {
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);

        this.criteria.forEach(builder::criterion);
        exporter.accept(recipeKey,
                        new StripRecipe(this.ingredient, this.output, this.experience, this.processTime),
                        builder.build(recipeKey.getValue().withPrefixedPath("recipe/" +
                                                                            this.category.getName() + "/")));
    }
}