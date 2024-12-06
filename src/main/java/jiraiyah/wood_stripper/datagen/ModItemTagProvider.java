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

import jiraiyah.wood_stripper.Main;
import jiraiyah.wood_stripper.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

import static jiraiyah.wood_stripper.Main.REFERENCE;

/**
 * The `ModItemTagProvider` class is responsible for generating and configuring item tags
 * for the mod. It extends the `FabricTagProvider.ItemTagProvider` to leverage the
 * functionality provided by the Fabric API for data generation.
 */
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider
{

    /**
     * Constructs a new `ModItemTagProvider` instance.
     *
     * @param output The `FabricDataOutput` used to output the generated data.
     * @param completableFuture A `CompletableFuture` that provides a `RegistryWrapper.WrapperLookup`
     *                          for accessing registry data during tag configuration.
     */
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    /**
     * Configures the item tags by adding various items to predefined tags.
     * This method is called during the data generation process to populate
     * the tags with the appropriate items from the mod.
     *
     * @param arg A `RegistryWrapper.WrapperLookup` that provides access to the registry data
     *            needed for configuring the tags.
     */
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        Main.LOGGER.logRGB256("Generating Item Tag Data", 0, 255, 0);

        getOrCreateTagBuilder(REFERENCE.STRIPPABLES);

        getOrCreateTagBuilder(REFERENCE.STRIPPED)
                .add(Items.STRIPPED_ACACIA_LOG)
                .add(Items.STRIPPED_BAMBOO_BLOCK)
                .add(Items.STRIPPED_BIRCH_LOG)
                .add(Items.STRIPPED_CHERRY_LOG)
                .add(Items.STRIPPED_CRIMSON_HYPHAE)
                .add(Items.STRIPPED_CRIMSON_STEM)
                .add(Items.STRIPPED_DARK_OAK_LOG)
                .add(Items.STRIPPED_JUNGLE_LOG)
                .add(Items.STRIPPED_MANGROVE_LOG)
                .add(Items.STRIPPED_OAK_LOG)
                .add(Items.STRIPPED_PALE_OAK_LOG)
                .add(Items.STRIPPED_SPRUCE_LOG)
                .add(Items.STRIPPED_WARPED_HYPHAE)
                .add(Items.STRIPPED_WARPED_STEM);
    }
}