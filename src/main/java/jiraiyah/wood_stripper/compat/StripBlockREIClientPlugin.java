/*
package jiraiyah.wood_stripper.compat;

import jiraiyah.wood_stripper.recipe.StripRecipe;
import jiraiyah.wood_stripper.registry.ModBlocks;
import jiraiyah.wood_stripper.registry.ModRecipes;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class StripBlockREIClientPlugin implements REIClientPlugin
{
    @Override
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new StripperBlockCategory());
        registry.addWorkstations(StripperBlockCategory.WOOD_STRIPPING, EntryStacks.of(ModBlocks.STRIPPER_BLOCK));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        registry.registerRecipeFiller(StripRecipe.class, ModRecipes.WOOD_STRIP_TYPE, StripperBlockDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry)
    {
        registry.registerClickArea(screen ->
                        new Rectangle(75, 30, 20, 30),
                                   jiraiyah.stripblock.screen.StripperBlockScreenRenderer.class, jiraiyah.stripblock.compat.StripperBlockCategory.WOOD_STRIPPING);
    }
}*/