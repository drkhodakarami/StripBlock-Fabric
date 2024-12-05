package jiraiyah.wood_stripper.registry;

import jiraiyah.register.Registers;
import jiraiyah.wood_stripper.blockentity.StripperBlockEntity;
import net.minecraft.block.entity.BlockEntityType;

import static jiraiyah.wood_stripper.Main.LOGGER;
import static jiraiyah.wood_stripper.Main.ModID;
import static jiraiyah.register.Registers.Entities.*;

public class ModBlockEntities
{
    public static BlockEntityType<StripperBlockEntity> STRIPPER_BLOCK_ENTITY;

    public static void init()
    {

        LOGGER.log("Registering Block Entities");

        Registers.init(ModID);

        STRIPPER_BLOCK_ENTITY = register("stripper_be", ModBlocks.STRIPPER_BLOCK, StripperBlockEntity::new);
    }
}