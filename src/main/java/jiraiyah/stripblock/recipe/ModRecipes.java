package jiraiyah.stripblock.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static jiraiyah.stripblock.Reference.*;

public class ModRecipes
{
    public static void register()
    {
        logN(">>> Registering Recipes for : " + ModID);

        Registry.register(Registries.RECIPE_SERIALIZER, identifier(StripperBlockRecipe.Serializer.ID),
                StripperBlockRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, identifier(StripperBlockRecipe.Type.ID),
                StripperBlockRecipe.Type.INSTANCE);
    }
}