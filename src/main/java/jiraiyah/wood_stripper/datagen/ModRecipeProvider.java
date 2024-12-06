/***********************************************************************************
 * Copyright (c) 2024 Alireza Khodakarami (Jiraiyah)                               *
 * ------------------------------------------------------------------------------- *
 * MIT License                                                                     *
 * =============================================================================== *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is           *
 * furnished to do so, subject to the following conditions:                        *
 * ------------------------------------------------------------------------------- *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 * ------------------------------------------------------------------------------- *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE   *
 * SOFTWARE.                                                                       *
 ***********************************************************************************/

package jiraiyah.wood_stripper.datagen;

import jiraiyah.jiralib.record.ConfiguredIngredient;
import jiraiyah.jiralib.record.OutputItemStackPayload;
import jiraiyah.wood_stripper.Main;
import jiraiyah.wood_stripper.recipe.StripRecipeBuilder;
import jiraiyah.wood_stripper.registry.ModBlocks;
import jiraiyah.wood_stripper.registry.ModRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static jiraiyah.wood_stripper.Main.ModID;
import static jiraiyah.wood_stripper.Main.REFERENCE;

/**
 * The {@code ModRecipeProvider} class is responsible for generating custom recipes for the mod.
 * It extends the {@code FabricRecipeProvider} to utilize the Fabric API's data generation capabilities.
 * This class defines various crafting, smelting, and other recipe types for custom items and blocks.
 */
public class ModRecipeProvider extends FabricRecipeProvider
{
    /**
     * Constructs a new {@code ModRecipeProvider} with the specified data output and registry lookup.
     *
     * @param output           The {@code FabricDataOutput} used to write the generated recipe data.
     * @param registriesFuture A {@code CompletableFuture} that provides access to the registry lookup.
     */
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    /**
     * Returns the empty string as the name.
     *
     * @return The empty string.
     */
    @Override
    public String getName()
    {
        return ModID;
    }

    /**
     * Returns a {@code RecipeGenerator} that is responsible for generating the recipes.
     * This method is overridden to define custom recipes for the mod, including crafting, smelting,
     * and other types of recipes.
     *
     * @param registries The {@code RegistryWrapper.WrapperLookup} providing access to the registries.
     * @param exporter   The {@code RecipeExporter} used to export the generated recipes.
     * @return A {@code RecipeGenerator} that generates the mod's custom recipes.
     */
    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter)
    {
        return new RecipeGenerator(registries, exporter)
        {
            @Override
            public void generate()
            {
                Main.LOGGER.logRGB256("Generating Recipe Data", 0, 255, 0);

                createShaped(RecipeCategory.TOOLS, ModBlocks.STRIPPER_BLOCK, 1)
                        .pattern("XBX")
                        .pattern("BCB")
                        .pattern("XBX")
                        .input('C', Items.CRAFTING_TABLE)
                        .input('X', Items.STONE_AXE)
                        .input('B', Items.IRON_BARS)
                        .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                        .criterion(hasItem(Items.STONE_PICKAXE), conditionsFromItem(Items.STONE_PICKAXE))
                        .criterion(hasItem(Items.IRON_BARS), conditionsFromItem(Items.IRON_BARS))
                        .offerTo(exporter, getRecipeName(ModBlocks.STRIPPER_BLOCK));

                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.ACACIA_LOG.asItem()),
                               Items.STRIPPED_ACACIA_LOG.getDefaultStack(),
                               0.0f, 0);

                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.BAMBOO_BLOCK.asItem()),
                               Items.STRIPPED_BAMBOO_BLOCK.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.BIRCH_LOG.asItem()),
                               Items.STRIPPED_BIRCH_LOG.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.CHERRY_LOG.asItem()),
                               Items.STRIPPED_CHERRY_LOG.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.CRIMSON_STEM.asItem()),
                               Items.STRIPPED_CRIMSON_STEM.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.DARK_OAK_LOG.asItem()),
                               Items.STRIPPED_DARK_OAK_LOG.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.JUNGLE_LOG.asItem()),
                               Items.STRIPPED_JUNGLE_LOG.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.MANGROVE_LOG.asItem()),
                               Items.STRIPPED_MANGROVE_LOG.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.OAK_LOG.asItem()),
                               Items.STRIPPED_OAK_LOG.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.SPRUCE_LOG.asItem()),
                               Items.STRIPPED_SPRUCE_LOG.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.WARPED_STEM.asItem()),
                               Items.STRIPPED_WARPED_STEM.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.CRIMSON_HYPHAE.asItem()),
                               Items.STRIPPED_CRIMSON_HYPHAE.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.WARPED_HYPHAE.asItem()),
                               Items.STRIPPED_WARPED_HYPHAE.getDefaultStack(), 0.0f, 0);
                offerStripping(exporter,
                               new ConfiguredIngredient(1, Blocks.PALE_OAK_LOG.asItem()),
                               Items.STRIPPED_PALE_OAK_LOG.getDefaultStack(), 0.0f, 0);
            }
        };
    }

    private static void offerStripping(RecipeExporter exporter, ConfiguredIngredient ingredient, ItemStack output, float experience, int processTime)
    {
        new StripRecipeBuilder(ModRecipes.WOOD_STRIPPING, RecipeCategory.BUILDING_BLOCKS,
                               ingredient, output, experience, processTime)
                .offerTo(exporter,
             RegistryKey.of(RegistryKeys.RECIPE,
                            REFERENCE.identifier("wood_stripper_" +
                                                 RecipeGenerator.getRecipeName(output.getItem()))));
    }
}