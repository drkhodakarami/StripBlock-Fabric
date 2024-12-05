package jiraiyah.wood_stripper.utils.slot;

import jiraiyah.jinventory.slots.PredicateSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;

public class InputSlot extends PredicateSlot
{

    public InputSlot(SimpleInventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack)
    {
        return stack.isIn(ItemTags.LOGS) ||
               stack.isOf(Items.BAMBOO_BLOCK) ||
               stack.isOf(Items.CRIMSON_STEM) ||
               stack.isOf(Items.WARPED_STEM);
    }
}