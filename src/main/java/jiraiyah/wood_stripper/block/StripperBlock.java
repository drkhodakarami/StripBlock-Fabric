package jiraiyah.wood_stripper.block;

import com.mojang.serialization.MapCodec;
import jiraiyah.jiralib.interfaces.ITickBE;
import jiraiyah.wood_stripper.registry.ModBlockEntities;
import jiraiyah.wood_stripper.blockentity.StripperBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static jiraiyah.wood_stripper.Main.REFERENCE;

public class StripperBlock extends Block implements BlockEntityProvider
{
    protected MapCodec<StripperBlock> CODEC = createCodec(StripperBlock::new);

    public static final Identifier ID = REFERENCE.identifier("stripper_block");

    public StripperBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected MapCodec<StripperBlock> getCodec()
    {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        world.playSound(player, pos, SoundEvents.BLOCK_CHERRY_WOOD_STEP, SoundCategory.BLOCKS, 1f, 1f);
        if (!world.isClient)
        {
            if(world.getBlockEntity(pos) instanceof StripperBlockEntity blockEntity)
                player.openHandledScreen(blockEntity);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
    {
        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StripperBlockEntity be)
            {
                for(Inventory inventory : be.getInventory().getInventories())
                    ItemScatterer.spawn(world, pos, inventory);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        return ITickBE.createTicker(world);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StripperBlockEntity(pos, state);
    }
}