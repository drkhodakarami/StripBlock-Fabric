package jiraiyah.stripblock;

import jiraiyah.stripblock.screen.ModScreenHandlers;
import jiraiyah.stripblock.screen.StripperBlockScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class StripBlockClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        HandledScreens.register(ModScreenHandlers.STRIPPER_SCREEN_HANDLER, StripperBlockScreen::new);
    }
}