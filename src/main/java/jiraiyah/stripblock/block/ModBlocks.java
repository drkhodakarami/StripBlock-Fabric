package jiraiyah.stripblock.block;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static jiraiyah.stripblock.Reference.*;

public class ModBlocks
{
    public static final Block STRIPPER_BLOCK = registerBlock("stripper_block",
            new StripperBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).requiresTool()));

    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, identifier(name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, identifier(name),
                new BlockItem(block, new Item.Settings()));
    }

    private static void addItemsToFunctionalItemGroup(FabricItemGroupEntries entries)
    {
        entries.add(STRIPPER_BLOCK);
    }

    public static void register()
    {
        logN(">>> Registering Blocks for : " + ModID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModBlocks::addItemsToFunctionalItemGroup);
    }
}