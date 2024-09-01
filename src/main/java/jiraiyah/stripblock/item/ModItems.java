package jiraiyah.stripblock.item;

import jiraiyah.stripblock.StripBlock;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static jiraiyah.stripblock.Reference.identifier;
import static jiraiyah.stripblock.Reference.logN;

public class ModItems
{
    //public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries)
    {
        //entries.add(RUBY);
    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, identifier(name), item);
    }

    public static void register()
    {
        logN(">>> Registering Items");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}