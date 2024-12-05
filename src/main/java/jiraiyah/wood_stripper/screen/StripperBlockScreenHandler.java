package jiraiyah.wood_stripper.screen;

import jiraiyah.jinventory.slots.OutputSlot;
import jiraiyah.jiralib.record.BlockPosPayload;
import jiraiyah.wood_stripper.blockentity.StripperBlockEntity;
import jiraiyah.wood_stripper.registry.ModBlocks;
import jiraiyah.wood_stripper.registry.ModScreenHandlers;
import jiraiyah.wood_stripper.utils.slot.InputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

import java.util.Objects;

public class StripperBlockScreenHandler extends ScreenHandler
{
    private final SimpleInventory output;
    private final SimpleInventory input;

    private final PropertyDelegate propertyDelegate;
    public final StripperBlockEntity blockEntity;
    private final ScreenHandlerContext context;

    public StripperBlockScreenHandler(int syncId, PlayerInventory inventory, BlockPosPayload payload)
    {
        this(syncId,
             inventory,
             (StripperBlockEntity) inventory.player.getWorld().getBlockEntity(payload.pos()),
             ((StripperBlockEntity) Objects.requireNonNull(inventory.player.getWorld().getBlockEntity(payload.pos()))).getDelegate());
    }

    @SuppressWarnings("DataFlowIssue")
    public StripperBlockScreenHandler(int syncId, PlayerInventory playerInventory, StripperBlockEntity blockEntity, PropertyDelegate propertyDelegate)
    {
        super(ModScreenHandlers.STRIPPER_SCREEN_HANDLER, syncId);

        checkDataCount(propertyDelegate, StripperBlockEntity.TOTAL_DELEGATE_COUNT);

        this.blockEntity = blockEntity;

        this.output = blockEntity.getInventory().getInventory(0);
        this.input = blockEntity.getInventory().getInventory(1);
        this.context = ScreenHandlerContext.create(blockEntity.getWorld(), blockEntity.getPos());

        playerInventory.onOpen(playerInventory.player);
        output.onOpen(playerInventory.player);
        input.onOpen(playerInventory.player);

        this.propertyDelegate = propertyDelegate;

        this.addSlot(new InputSlot(input, 0, 26, 14));
        this.addSlot(new OutputSlot(output, 0, 134, 14));

        addPlayerSlots(playerInventory, 8, 51);
        addPlayerHotbarSlots(playerInventory, 8, 109);

        addProperties(propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot)
    {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.input.size() + this.output.size())
            {
                if (!this.insertItem(originalStack, this.input.size() + this.output.size(),
                                     this.slots.size(), true))
                    return ItemStack.EMPTY;
            }
            else if (!this.insertItem(originalStack, 0,
                                      this.input.size() + this.output.size(), false))
                return ItemStack.EMPTY;

            if (originalStack.isEmpty())
                slot.setStack(ItemStack.EMPTY);
            else
                slot.markDirty();
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return canUse(this.context, player, ModBlocks.STRIPPER_BLOCK);
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
}