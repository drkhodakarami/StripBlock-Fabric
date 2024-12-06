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
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * The `ModLootTableProvider` class is responsible for generating loot tables for various blocks in the mod.
 * It extends the `FabricBlockLootTableProvider` to utilize its functionality for creating loot tables.
 * This class is part of the data generation process and is used to define how blocks drop items when broken.
 *
 * <p>This class makes use of organization-specific modules and follows the patterns established in the codebase
 * for defining loot tables, including custom ore drops and special block drops.</p>
 */
@SuppressWarnings("ALL")
public class ModLootTableProvider extends FabricBlockLootTableProvider
{
    /**
     * Constructs a new `ModLootTableProvider` instance.
     *
     * @param dataOutput     The `FabricDataOutput` instance used for data generation output.
     * @param registryLookup A `CompletableFuture` that provides access to the `RegistryWrapper.WrapperLookup`,
     *                       which is used to access registries needed for loot table generation.
     */
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup)
    {
        super(dataOutput, registryLookup);
    }

    /**
     * Generates the loot tables for the mod's blocks.
     * This method is overridden from the `FabricBlockLootTableProvider` and is called during the data generation process.
     * It defines the loot tables for various blocks, including gems, raw materials, special blocks, ores, machines, and goo.
     * The method uses organization-specific logging to indicate the start of the loot table generation process.
     */
    @Override
    public void generate()
    {
        Main.LOGGER.logRGB256("Generating Loot Table Data", 0, 255, 0);

        addDrop(ModBlocks.STRIPPER_BLOCK);
    }
}