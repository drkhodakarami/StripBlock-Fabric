package jiraiyah.wood_stripper.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.RecipeInput;

public class StripSerializer implements RecipeSerializer<StripRecipe>
{
    @Override
    public MapCodec<StripRecipe> codec()
    {
        return StripRecipe.CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, StripRecipe> packetCodec()
    {
        return StripRecipe.PACKET_CODEC;
    }

    public StripSerializer()
    {}
}