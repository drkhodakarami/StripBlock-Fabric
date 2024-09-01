package jiraiyah.stripblock.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static jiraiyah.stripblock.Reference.*;

public class ModRecipes
{
    public static final RecipeType<StripRecipe> WOOD_STRIP_TYPE =
            Registry.register(Registries.RECIPE_TYPE, identifier("wood_stripping"),
                              new RecipeType<StripRecipe>()
                                    {
                                        public String toString()
                                        {
                                            return "wood_stripping";
                                        }
                                    });

    public static final RecipeSerializer<StripRecipe> WOOD_STRIP_SERIALIZER =
            Registry.register(Registries.RECIPE_SERIALIZER, "",
                              new StripSerializer(StripRecipe::new, 0));

    public static void register()
    {
        logN(">>> Registering Recipes for : " + ModID);

        Registry.register(Registries.RECIPE_SERIALIZER, identifier(StripperBlockRecipe.Serializer.ID),
                StripperBlockRecipe.Serializer.INSTANCE);

    }
}