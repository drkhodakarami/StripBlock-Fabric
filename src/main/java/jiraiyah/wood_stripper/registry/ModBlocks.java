package jiraiyah.wood_stripper.registry;

import jiraiyah.register.Registers;
import jiraiyah.wood_stripper.block.StripperBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;

import static jiraiyah.wood_stripper.Main.ModID;
import static jiraiyah.wood_stripper.Main.LOGGER;
import static jiraiyah.register.Registers.Block.*;

public class ModBlocks
{
    public static StripperBlock STRIPPER_BLOCK;


    public static void init()
    {
        LOGGER.log("Registering Blocks");

        Registers.init(ModID);

        STRIPPER_BLOCK = register("stripper_block",
                                  AbstractBlock.Settings.copy(Blocks.OAK_WOOD).requiresTool(),
                                  StripperBlock::new);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(STRIPPER_BLOCK));
    }
}