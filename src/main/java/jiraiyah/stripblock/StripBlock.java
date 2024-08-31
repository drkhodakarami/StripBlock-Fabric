package jiraiyah.stripblock;

import jiraiyah.stripblock.block.ModBlocks;
import jiraiyah.stripblock.block.entity.ModBlockEntities;
import jiraiyah.stripblock.item.ModItemGroups;
import jiraiyah.stripblock.item.ModItems;
import jiraiyah.stripblock.recipe.ModRecipes;
import jiraiyah.stripblock.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jiraiyah.stripblock.Reference.ModID;
import static jiraiyah.stripblock.Reference.log;

public class StripBlock implements ModInitializer
{

	@Override
	public void onInitialize()
	{
		log(">>> Initializing : " + ModID);

		ModItemGroups.register();
		ModItems.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModScreenHandlers.register();
		ModRecipes.register();
	}
}