package jiraiyah.stripblock.block.entity.slot;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class InputSlot extends Slot
{
    public InputSlot(Inventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack)
    {
        return stack.isOf(Items.BAMBOO_BLOCK) ||
                stack.isOf(Items.ACACIA_LOG) ||
                stack.isOf(Items.BIRCH_LOG) ||
                stack.isOf(Items.CHERRY_LOG) ||
                stack.isOf(Items.CRIMSON_STEM) ||
                stack.isOf(Items.DARK_OAK_LOG) ||
                stack.isOf(Items.JUNGLE_LOG) ||
                stack.isOf(Items.MANGROVE_LOG) ||
                stack.isOf(Items.OAK_LOG) ||
                stack.isOf(Items.SPRUCE_LOG) ||
                stack.isOf(Items.WARPED_STEM);
    }
}