package jiraiyah.wood_stripper.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jiraiyah.jinventory.RecipeSimpleInventory;
import jiraiyah.wood_stripper.registry.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

// Big shout and thanks to "ZeroNoRyouki" for helping me on finding proper solution for custom recipe/serializer
public record StripRecipe(String group, CraftingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int processTime) implements Recipe<RecipeSimpleInventory>
{
    public static final MapCodec<StripRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.STRING.fieldOf("group").forGetter(StripRecipe::group),
            CraftingRecipeCategory.CODEC.fieldOf("category").forGetter(StripRecipe::category),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(StripRecipe::ingredient),
            ItemStack.UNCOUNTED_CODEC.fieldOf("result").forGetter(StripRecipe::result),
            Codec.FLOAT.fieldOf("experience").forGetter(StripRecipe::experience),
            Codec.INT.fieldOf("processTime").forGetter(StripRecipe::processTime)
    ).apply(inst, StripRecipe::new));

    public static final PacketCodec<RegistryByteBuf, StripRecipe> PACKET_CODEC =
            PacketCodec.tuple(PacketCodecs.STRING, StripRecipe::group,
                              CraftingRecipeCategory.PACKET_CODEC, StripRecipe::category,
                              Ingredient.PACKET_CODEC, StripRecipe::ingredient,
                              ItemStack.PACKET_CODEC, StripRecipe::result,
                              PacketCodecs.FLOAT, StripRecipe::experience,
                              PacketCodecs.INTEGER, StripRecipe::processTime,
                              StripRecipe::new);


    @Override
    public boolean matches(RecipeSimpleInventory input, World world)
    {
        if(world.isClient)
            return false;
        return this.ingredient.test(input.getHeldStacks().getFirst());
    }

    @Override
    public ItemStack craft(RecipeSimpleInventory input, RegistryWrapper.WrapperLookup lookup)
    {
        return this.result.copy();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeSimpleInventory>> getSerializer()
    {
        return ModRecipes.WOOD_STRIP_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeSimpleInventory>> getType()
    {
        return ModRecipes.WOOD_STRIP_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement()
    {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
    }
}