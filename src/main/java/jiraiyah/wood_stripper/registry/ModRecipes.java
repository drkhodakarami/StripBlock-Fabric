package jiraiyah.wood_stripper.registry;

import jiraiyah.register.Registers;
import jiraiyah.wood_stripper.recipe.StripRecipe;
import jiraiyah.wood_stripper.recipe.StripSerializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import static jiraiyah.wood_stripper.Main.ModID;
import static jiraiyah.wood_stripper.Main.LOGGER;
//import static jiraiyah.register.Registers.Recipe.*;


//Big shout and thanks to "Linguardium" for helping me identifying errors in the registration and recipe definition
public class ModRecipes
{
    public static RecipeType<StripRecipe> WOOD_STRIP_TYPE;
    public static RecipeSerializer<StripRecipe> WOOD_STRIP_SERIALIZER;

    public static final String WOOD_STRIPPING = "wood_stripping";

    public static void init()
    {
        LOGGER.log("Registering Recipes");

        Registers.init(ModID);

        WOOD_STRIP_TYPE = register(WOOD_STRIPPING,
                                   new RecipeType<>()
                                   {
                                       public String toString()
                                       {
                                           return WOOD_STRIPPING;
                                       }
                                   });

        WOOD_STRIP_SERIALIZER = register(WOOD_STRIPPING, new StripSerializer());

    }

    public static <T extends Recipe<D>, D extends RecipeInput> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        RegistryKey<RecipeSerializer<?>> key = Registers.getKey(name, RegistryKeys.RECIPE_SERIALIZER);
        return Registry.register(Registries.RECIPE_SERIALIZER, key, serializer);
    }

    public static <T extends Recipe<D>, D extends RecipeInput> RecipeType<T> register(String name, RecipeType<T> recipeType) {
        RegistryKey<RecipeType<?>> key = Registers.getKey(name, RegistryKeys.RECIPE_TYPE);
        return Registry.register(Registries.RECIPE_TYPE, key, recipeType);
    }
}