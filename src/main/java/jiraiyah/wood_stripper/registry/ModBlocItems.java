package jiraiyah.wood_stripper.registry;

import jiraiyah.register.Registers;
import net.minecraft.item.BlockItem;

import static jiraiyah.register.Registers.BlockItem.*;
import static jiraiyah.wood_stripper.Main.LOGGER;
import static jiraiyah.wood_stripper.Main.ModID;

public class ModBlocItems
{
    public static BlockItem STRIPPER_BLOCK;

    public static void init()
    {
        LOGGER.log("Registering Block Items");

        Registers.init(ModID);

        STRIPPER_BLOCK = register(ModBlocks.STRIPPER_BLOCK);
    }
}