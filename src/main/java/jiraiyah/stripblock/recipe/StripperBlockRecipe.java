package jiraiyah.stripblock.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class StripperBlockRecipe implements Recipe<SimpleInventory>
{
    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    public StripperBlockRecipe(List<Ingredient> ingredients, ItemStack itemStack)
    {
        this.output = itemStack;
        this.recipeItems = ingredients;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world)
    {
        if(world.isClient)
            return false;
        // recipeItem index is coming from the list in the json file, the inventory index one, is coming from the slot in the gui
        return recipeItems.getFirst().test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory input, RegistryWrapper.WrapperLookup lookup)
    {
        return output;
    }

    @Override
    public boolean fits(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup)
    {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients()
    {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<StripperBlockRecipe>
    {
        public static final Type INSTANCE = new Type();
        public static String ID = "wood_stripping";
    }

    public static class Serializer implements RecipeSerializer<StripperBlockRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "wood_stripping";

        // A codec is a way to read a json file and convert it to a class on the fly
        public static final MapCodec<StripperBlockRecipe> CODEC = RecordCodecBuilder.create(in ->
                in.group(
                    validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(StripperBlockRecipe::getIngredients),
                    RecipeCodecs.CRAFTING_RESULT.fieldOf("output").forGetter(r -> r.output)
                ).apply(in, StripperBlockRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max)
        {
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)),
                    list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public MapCodec<StripperBlockRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, StripperBlockRecipe> packetCodec()
        {
            return null;
        }

        @Override
        public StripperBlockRecipe read(PacketByteBuf buf)
        {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++)
            {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new StripperBlockRecipe(inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, StripperBlockRecipe recipe)
        {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients())
            {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getResult(null));
        }
    }
}