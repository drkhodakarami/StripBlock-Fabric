package jiraiyah.stripblock.block.entity;

import jiraiyah.stripblock.data.StripperData;
import jiraiyah.stripblock.recipe.ModRecipes;
import jiraiyah.stripblock.recipe.StripRecipe;
import jiraiyah.stripblock.screen.StripperBlockScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StripperBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory
{
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private final RecipeManager.MatchGetter<SingleStackRecipeInput, StripRecipe> matchGetter;

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int max_progress = 9;

    public static final int TOTAL_SLOTS = 2;
    public static final int TOTAL_DELEGATE_COUNT = 2;

    public StripperBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.STRIPPER_BLOCK_ENTITY, pos, state);
        this.matchGetter = RecipeManager.createCachedMatchGetter(ModRecipes.WOOD_STRIP_TYPE);
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
    public Text getDisplayName()
    {
        return Text.translatable("strip_block.strip_block_entity");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("stripper_block.progress", progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("stripper_block.progress");
    }

    /*@Override
    public Object getScreenOpeningData(ServerPlayerEntity player)
    {
        return new StripperData(pos);
    }*/

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StripperBlockScreenHandler(syncId, playerInventory, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }



    public void tick(World world, BlockPos pos, BlockState state)
    {
        if(world.isClient)
            return;

        if(inventory.getFirst().isEmpty())
            return;

        StripperBlockEntity blockEntity = null;
        if(world.getBlockEntity(pos) instanceof StripperBlockEntity)
            blockEntity = (StripperBlockEntity)world.getBlockEntity(pos);

        if(blockEntity == null)
            return;

        if(isOutputSlotReceivable())
        {
            if(this.hasRecipe(blockEntity, world.getRegistryManager()))
            {
                this.increaseCraftProgress();
                if(isCraftingFinished())
                {
                    this.craftItem(blockEntity, world.getRegistryManager());
                    this.resetProgress();
                    markDirty(world, pos, state);
                }
            }
            else if(blockEntity.progress != 0)
            {
                this.resetProgress();
                markDirty(world, pos, state);
            }
        }
        else if(blockEntity.progress != 0)
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

    private boolean hasRecipe(StripperBlockEntity blockEntity, DynamicRegistryManager registryManager)
    {
        RecipeEntry<?> recipeEntry = (RecipeEntry<?>)blockEntity.matchGetter.getFirstMatch(new SingleStackRecipeInput(blockEntity.inventory.getFirst()), world).orElse(null);

        return recipeEntry != null &&
                canInsertAmountToOutput(recipeEntry.value().getResult(registryManager)) &&
                canInsertItemToOutput(recipeEntry.value().getResult(registryManager).getItem());
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
    }

    private boolean isCraftingFinished()
    {
        return progress >= max_progress;
    }

    private void craftItem(StripperBlockEntity blockEntity, DynamicRegistryManager registryManager)
    {
        RecipeEntry<?> recipeEntry = (RecipeEntry<?>) blockEntity.matchGetter
                .getFirstMatch(new SingleStackRecipeInput(blockEntity.inventory.getFirst()), world).orElse(null);
        if(recipeEntry != null)
        {
            this.removeStack(INPUT_SLOT, 1);

            this.setStack(OUTPUT_SLOT,
                          new ItemStack(recipeEntry.value().getResult(registryManager).getItem(),
                                         getStack(OUTPUT_SLOT).getCount() + recipeEntry.value().getResult(registryManager).getCount()));
        }
    }

    private void resetProgress()
    {
        this.progress = 0;
    }
}