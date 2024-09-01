package jiraiyah.stripblock.screen;

import jiraiyah.stripblock.block.entity.slot.InputSlot;
import jiraiyah.stripblock.block.entity.StripperBlockEntity;
import jiraiyah.stripblock.block.entity.slot.OutputSlot;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class StripperBlockScreenHandler extends ScreenHandler
{
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    //public final StripperBlockEntity blockEntity;

    public StripperBlockScreenHandler(int syncId, PlayerInventory inventory)
    {
        this(syncId, inventory, new ArrayPropertyDelegate(2));
    }

    public StripperBlockScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate propertyDelegate)
    {
        super(ModScreenHandlers.STRIPPER_SCREEN_HANDLER, syncId);
        checkDataCount(propertyDelegate, StripperBlockEntity.TOTAL_DELEGATE_COUNT);
        this.inventory = new SimpleInventory(StripperBlockEntity.TOTAL_SLOTS);
        playerInventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new InputSlot(inventory, 0, 80, 11));
        this.addSlot(new OutputSlot(inventory, 1, 80, 59));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot)
    {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public boolean isCrafting()
    {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress(int progressArrowSize)
    {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}