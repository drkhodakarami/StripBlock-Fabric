package jiraiyah.stripblock.recipe;

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
    private final StripRecipe.RecipeFactory<StripRecipe> recipeFactory;
    private final MapCodec<StripRecipe> codec;
    private final PacketCodec<RegistryByteBuf, StripRecipe> packetCodec;

    public StripSerializer(StripRecipe.RecipeFactory<StripRecipe> recipeFactory, int processTime)
    {
        this.recipeFactory = recipeFactory;
        this.codec = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                        CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                        ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                        Codec.FLOAT.fieldOf("experience").orElse(0.0f).forGetter(recipe -> recipe.experience),
                        Codec.INT.fieldOf("processTime").orElse(processTime).forGetter(recipe -> recipe.processTime)

                ).apply(instance, recipeFactory::create)
        );
        this.packetCodec = PacketCodec.ofStatic(this::write, this::read);
    }

    @Override
    public MapCodec<StripRecipe> codec()
    {
        return this.codec;
    }

    @Override
    public PacketCodec<RegistryByteBuf, StripRecipe> packetCodec()
    {
        return this.packetCodec;
    }

    private StripRecipe read(RegistryByteBuf buf)
    {
        String group = buf.readString();
        CraftingRecipeCategory recipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);
        Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
        ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
        float experience = buf.readFloat();
        int processTime = buf.readVarInt();
        return this.recipeFactory.create(group, recipeCategory, ingredient, itemStack, experience, processTime);
    }

    private void write(RegistryByteBuf buf, StripRecipe recipe)
    {
        buf.writeString(recipe.group);
        buf.writeEnumConstant(recipe.getCategory());
        Ingredient.PACKET_CODEC.encode(buf, recipe.ingredient);
        ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        buf.writeFloat(recipe.experience);
        buf.writeVarInt(recipe.processTime);
    }

    public StripRecipe create(String group, CraftingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int processTime) {
        return this.recipeFactory.create(group, category, ingredient, result, experience, processTime);
    }
}