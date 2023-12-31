package jiraiyah.stripblock.recipe;

import jiraiyah.stripblock.StripBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes
{
    public static void register()
    {
        StripBlock.LOGGER.info(">>> Registering Recipes for : " + StripBlock.ModID);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(StripBlock.ModID, StripperBlockRecipe.Serializer.ID),
                StripperBlockRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(StripBlock.ModID, StripperBlockRecipe.Type.ID),
                StripperBlockRecipe.Type.INSTANCE);
    }
}