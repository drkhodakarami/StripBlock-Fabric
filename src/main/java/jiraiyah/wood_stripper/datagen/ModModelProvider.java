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

import jiraiyah.jiralib.block.AbstractActivatableBlock;
import jiraiyah.wood_stripper.Main;
import jiraiyah.wood_stripper.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.item.Item;

/**
 * The `ModModelProvider` class is responsible for generating block and item models
 * for the mod. It extends the `FabricModelProvider` to utilize the Fabric API's
 * data generation capabilities.
 */
public class ModModelProvider extends FabricModelProvider
{
    private static BlockStateModelGenerator.BlockTexturePool citrine_pool, ruby_pool, sapphire_pool, enderite_pool, oakPlank;
    /**
     * Constructs a new `ModModelProvider` instance with the specified data output.
     *
     * @param output The `FabricDataOutput` instance used for data generation.
     */
    public ModModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    /**
     * Generates block state models using the provided `BlockStateModelGenerator`.
     * This method registers various block models, including simple cube models,
     * stairs, slabs, and other special block types. It also logs the generation
     * process using RGB color formatting.
     *
     * @param generator The `BlockStateModelGenerator` used to register block models.
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator)
    {
        Main.LOGGER.logRGB256("Generating Block Model Data", 0, 255, 0);

        generator.registerSingleton(ModBlocks.STRIPPER_BLOCK, TexturedModel.ORIENTABLE);
    }

    /**
     * Generates item models using the provided `ItemModelGenerator`.
     * This method registers various item models, including food, tools, armor,
     * and other miscellaneous items. It also logs the generation process using
     * RGB color formatting.
     *
     * @param generator The `ItemModelGenerator` used to register item models.
     */
    @Override
    public void generateItemModels(ItemModelGenerator generator)
    {
        Main.LOGGER.logRGB256("Generating Item Model Data", 0, 255, 0);
    }
}