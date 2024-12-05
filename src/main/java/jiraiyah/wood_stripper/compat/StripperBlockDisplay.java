/*
package jiraiyah.wood_stripper.compat;

import jiraiyah.wood_stripper.recipe.StripRecipe;
import jiraiyah.wood_stripper.registry.ModRecipes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.recipe.RecipeEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StripperBlockDisplay  extends BasicDisplay
{
    public StripperBlockDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs)
    {
        super(inputs, outputs);
    }

    public StripperBlockDisplay(RecipeEntry<StripRecipe> recipe)
    {
        super(getInputList(recipe.value()),
                List.of(EntryIngredient.of(EntryStacks.of(recipe.value().result()))));
    }

    private static List<EntryIngredient> getInputList(StripRecipe recipe)
    {
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.ingredient()));
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return StripperBlockCategory.WOOD_STRIPPING;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer()
    {
        return null;
    }
}*/