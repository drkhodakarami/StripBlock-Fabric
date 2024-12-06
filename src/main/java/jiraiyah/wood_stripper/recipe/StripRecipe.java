package jiraiyah.wood_stripper.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jiraiyah.jinventory.RecipeSimpleInventory;
import jiraiyah.jiralib.record.ConfiguredIngredient;
import jiraiyah.wood_stripper.registry.ModBlocks;
import jiraiyah.wood_stripper.registry.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

import static jiraiyah.wood_stripper.Main.REFERENCE;

// Big shout and thanks to "ZeroNoRyouki" for helping me on finding proper solution for custom recipe/serializer
public record StripRecipe(ConfiguredIngredient ingredient,
                          ItemStack result,
                          float experience,
                          int processTime) implements Recipe<RecipeSimpleInventory>
{
    public static final MapCodec<StripRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ConfiguredIngredient.CODEC.fieldOf("ingredient").forGetter(StripRecipe::ingredient),
            ItemStack.UNCOUNTED_CODEC.fieldOf("result").forGetter(StripRecipe::result),
            Codec.FLOAT.fieldOf("experience").forGetter(StripRecipe::experience),
            Codec.INT.fieldOf("processTime").forGetter(StripRecipe::processTime)
    ).apply(inst, StripRecipe::new));

    public static final PacketCodec<RegistryByteBuf, StripRecipe> PACKET_CODEC =
            PacketCodec.tuple(ConfiguredIngredient.PACKET_CODEC, StripRecipe::ingredient,
                              ItemStack.PACKET_CODEC, StripRecipe::result,
                              PacketCodecs.FLOAT, StripRecipe::experience,
                              PacketCodecs.INTEGER, StripRecipe::processTime,
                              StripRecipe::new);


    @Override
    public boolean matches(RecipeSimpleInventory input, World world)
    {
        ItemStack stack = input.getStack(0);
        return this.ingredient.testForRecipe(stack);
    }

    @Override
    public ItemStack craft(RecipeSimpleInventory input, RegistryWrapper.WrapperLookup lookup)
    {
        ItemStack stack = this.ingredient.testForRecipe(input.getStack(0))
                ? input.getStack(0) : ItemStack.EMPTY;

        stack.decrement(this.ingredient.stackData().count());

        input.setStack(0, stack);

        return this.result.copy();
    }

    @Override
    public String getGroup() {
        return REFERENCE.identifier("wood_stripper").toString();
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
    public boolean isIgnoredInRecipeBook()
    {
        return true;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
    }

    @Override
    public List<RecipeDisplay> getDisplays()
    {
        return List.of(new StripDisplay(
                this.ingredient.toDisplay(),
                new SlotDisplay.StackSlotDisplay(this.result),
                new SlotDisplay.ItemSlotDisplay(ModBlocks.STRIPPER_BLOCK.asItem()),
                this.processTime
        ));
    }
}