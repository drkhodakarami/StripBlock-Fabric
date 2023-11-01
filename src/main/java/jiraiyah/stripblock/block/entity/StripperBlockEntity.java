package jiraiyah.stripblock.block.entity;

import jiraiyah.stripblock.recipe.StripperBlockRecipe;
import jiraiyah.stripblock.screen.StripperBlockScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StripperBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory
{
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int max_progress = 9;

    public StripperBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.STRIPPER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate()
        {
            @Override
            public int get(int index)
            {
                return switch (index)
                {
                    case 0 -> StripperBlockEntity.this.progress;
                    case 1 -> StripperBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value)
            {
                switch (index)
                {
                    case 0 -> StripperBlockEntity.this.progress = value;
                    case 1 -> StripperBlockEntity.this.max_progress = value;
                };
            }

            @Override
            public int size()
            {
                return 2;
            }
        };
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side)
    {
        return side != Direction.DOWN && slot == INPUT_SLOT;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side)
    {
        return side == Direction.DOWN && slot == OUTPUT_SLOT;
    }

    @Override
    protected void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("stripper_block.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("stripper_block.progress");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf)
    {
        // Sending Block Entity position in the world from server to client and reverse
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("strip_block.strip_block_entity");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StripperBlockScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public void tick(World world, BlockPos pos, BlockState state)
    {
        if(world.isClient)
            return;

        if(isOutputSlotReceivable())
        {
            if(this.hasRecipe())
            {
                this.increaseCraftProgress();
                markDirty(world, pos, state);
                if(isCraftingFinished())
                {
                    this.craftItem();
                    this.resetProgress();
                }
            }
            else
            {
                this.resetProgress();
            }
        }
        else
        {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private boolean isOutputSlotReceivable()
    {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe()
    {
        Optional<RecipeEntry<StripperBlockRecipe>> recipe = getCurrentRecipe();

        return recipe.isPresent() &&
                canInsertAmountToOutput(recipe.get().value().getResult(null)) &&
                canInsertItemToOutput(recipe.get().value().getResult(null).getItem());
    }

    private Optional<RecipeEntry<StripperBlockRecipe>> getCurrentRecipe()
    {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < this.size(); i++)
            inv.setStack(i, this.getStack(i));

        return getWorld().getRecipeManager().getFirstMatch(StripperBlockRecipe.Type.INSTANCE, inv, getWorld());
    }

    private boolean canInsertAmountToOutput(ItemStack result)
    {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean canInsertItemToOutput(Item item)
    {
        return this.getStack(OUTPUT_SLOT).getItem() == item ||
                this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private void increaseCraftProgress()
    {
        this.progress++;
        //Blocks.STRIPPED_BAMBOO_BLOCK <- Blocks.BAMBOO_BLOCK
        //Blocks.STRIPPED_ACACIA_LOG <- Blocks.ACACIA_LOG
        //Blocks.STRIPPED_BIRCH_LOG <- Blocks.BIRCH_LOG
        //Blocks.STRIPPED_CHERRY_LOG <- Blocks.CHERRY_LOG
        //Blocks.STRIPPED_CRIMSON_STEM <- Blocks.CRIMSON_STEM
        //Blocks.STRIPPED_DARK_OAK_LOG <- Blocks.DARK_OAK_LOG
        //Blocks.STRIPPED_JUNGLE_LOG <- Blocks.JUNGLE_LOG
        //Blocks.STRIPPED_MANGROVE_LOG <- Blocks.MANGROVE_LOG
        //Blocks.STRIPPED_OAK_LOG <- Blocks.OAK_LOG
        //Blocks.STRIPPED_SPRUCE_LOG <- Blocks.SPRUCE_LOG
        //Blocks.STRIPPED_WARPED_STEM <- Blocks.WARPED_STEM
    }

    private boolean isCraftingFinished()
    {
        return progress >= max_progress;
    }

    private void craftItem()
    {
        Optional<RecipeEntry<StripperBlockRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT, 1);

        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(),
                getStack(OUTPUT_SLOT).getCount() + recipe.get().value().getResult(null).getCount()));
    }

    private void resetProgress()
    {
        this.progress = 0;
    }
}