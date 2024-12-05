package jiraiyah.wood_stripper;

import jiraiyah.logger.Logger;
import jiraiyah.register.Registers;
import jiraiyah.wood_stripper.registry.*;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer
{
	public static final String ModID = "wood_stripper";

	public static final Logger LOGGER = new Logger(ModID);
	public static final Reference REFERENCE = new Reference(ModID);

	@Override
	public void onInitialize()
	{
		LOGGER.logMain();

		Registers.init(ModID);

		ModBlocks.init();
		ModBlocItems.init();
		ModBlockEntities.init();
		ModScreenHandlers.init();
		ModRecipes.init();
	}
}