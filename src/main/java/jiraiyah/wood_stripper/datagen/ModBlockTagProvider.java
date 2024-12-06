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
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

/**
 * The `ModBlockTagProvider` class is responsible for generating block tag data for the mod.
 * It extends the `FabricTagProvider.BlockTagProvider` to utilize the Fabric API's data generation capabilities.
 * This class defines various block tags and associates them with specific blocks from the mod.
 */
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider
{
    /**
     * Constructs a new `ModBlockTagProvider` instance.
     *
     * @param output           The `FabricDataOutput` instance used for data generation output.
     * @param registriesFuture A `CompletableFuture` that provides access to the `RegistryWrapper.WrapperLookup`,
     *                         which contains registry data needed for tag configuration.
     */
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    /**
     * Configures the block tags by associating specific blocks with predefined tags.
     * This method is called during the data generation process to define the relationships
     * between blocks and their respective tags.
     *
     * @param arg The `RegistryWrapper.WrapperLookup` instance providing access to the registry data.
     */
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        Main.LOGGER.logRGB256("Generating Block Tag Data", 0, 255, 0);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.STRIPPER_BLOCK);
    }
}