package jiraiyah.stripblock;

import jiraiyah.stripblock.block.ModBlocks;
import jiraiyah.stripblock.item.ModItemGroups;
import jiraiyah.stripblock.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StripBlock implements ModInitializer
{
	public static final String ModID = "strip_block";
	public static final Logger LOGGER = LoggerFactory.getLogger(ModID);

	@Override
	public void onInitialize()
	{
		LOGGER.info(">>> Initializing : " + ModID);

		ModItemGroups.register();
		ModItems.register();
		ModBlocks.register();
	}
}