package jiraiyah.wood_stripper.blockentity;

import jiraiyah.jinventory.OutputSimpleInventory;
import jiraiyah.jinventory.SyncingSimpleInventory;
import jiraiyah.jinventory.blockentity.AbstractInventoryBE;
import jiraiyah.jiralib.interfaces.ISync;
import jiraiyah.jiralib.record.BlockPosPayload;
import jiraiyah.reference.BEKeys;
import jiraiyah.wood_stripper.recipe.StripRecipe;
import jiraiyah.wood_stripper.registry.ModBlockEntities;
import jiraiyah.wood_stripper.registry.ModRecipes;
import jiraiyah.wood_stripper.screen.StripperBlockScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static jiraiyah.wood_stripper.Main.ModID;
import static jiraiyah.wood_stripper.Main.REFERENCE;

public class StripperBlockEntity extends AbstractInventoryBE implements ExtendedScreenHandlerFactory<BlockPosPayload>
{
    public static final Text TITLE = REFERENCE.translateContainer("wood.stripper.screen");

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int max_progress = 0;

    public static final int TOTAL_DELEGATE_COUNT = 2;

    public StripperBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.STRIPPER_BLOCK_ENTITY, pos, state);

        getInventory().addInventory(new SyncingSimpleInventory(this, 1));
        getInventory().addInventory(new OutputSimpleInventory(this, 1), Direction.DOWN);

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
                }
            }

            @Override
            public int size()
            {
                return 2;
            }
        };
    }

    @Override
    public Text getDisplayName()
    {
        return TITLE;
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity player)
    {
        return new BlockPosPayload(this.pos);
    }

    @Override
    public List<ISync> getSyncables()
    {
        return List.of(
                (SyncingSimpleInventory) getInventory().getInventory(0),
                (OutputSimpleInventory) getInventory().getInventory(1));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt(ModID + BEKeys.PROGRESS_AMOUNT, progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.readNbt(nbt, registryLookup);
        progress = nbt.getInt(ModID + BEKeys.PROGRESS_AMOUNT);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StripperBlockScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void onTick()
    {
        if(world == null || world.isClient)
            return;

        if(!(world instanceof ServerWorld sw))
        {
            progress = 0;
            return;
        }

        var input = getInventory().getInventory(0);
        var output= getInventory().getInventory(1);

        if(input == null || output == null)
        {
            progress = 0;
            return;
        }

        if(input.isEmpty())
        {
            progress = 0;
            return;
        }

        Optional<RecipeEntry<StripRecipe>> recipeEntry = getCurrentRecipe(sw);

        if(recipeEntry.isEmpty())
        {
            progress = 0;
            return;
        }

        this.max_progress = recipeEntry.get().value().processTime();

        if(!outputCanAccept(recipeEntry.get().value().result()))
        {
            progress = 0;
            return;
        }

        this.progress++;
        if(this.progress >= this.max_progress)
        {
            ItemStack craftResult = recipeEntry.get().value().craft(getInventory().getRecipeInventory(), world.getRegistryManager());
            if (outputCanAccept(craftResult))
            {
                if (!craftResult.isEmpty())
                {
                    output.setStack(0,
                                    new ItemStack(craftResult.getItem(),
                                                  output.getStack(0).getCount() + craftResult.getCount()));
                    this.progress = 0;
                    update();
                }
            }
            else if (this.progress != 0)
            {
                this.progress = 0;
                update();
            }
        }
    }

    private boolean outputCanAccept(ItemStack item)
    {
        var output = getInventory().getInventory(1);
        if(output == null)
            return false;
        var stack = output.getStack(0);
        return stack.isEmpty() ||
               (stack.isOf(item.getItem()) && stack.getCount() <= stack.getMaxCount() - item.getCount());
    }

    private Optional<RecipeEntry<StripRecipe>> getCurrentRecipe(ServerWorld sw)
    {
        return sw.getRecipeManager().getFirstMatch(ModRecipes.WOOD_STRIP_TYPE, getInventory().getRecipeInventory(), this.world);
    }

    public PropertyDelegate getDelegate()
    {
        return this.propertyDelegate;
    }

    public InventoryStorage getProvider(Direction direction)
    {
        var storage = direction == Direction.DOWN
                      ? getInventoryStorage(direction)
                      : getInventoryStorage(null);
        return storage;
    }
}