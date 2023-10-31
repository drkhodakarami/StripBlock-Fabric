package jiraiyah.stripblock;

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
		LOGGER.info("Hello Fabric world!");
	}
}