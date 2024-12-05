package jiraiyah.wood_stripper;

import jiraiyah.wood_stripper.registry.ModScreenHandlers;
import jiraiyah.wood_stripper.screen.StripperBlockScreenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class Client implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        HandledScreens.register(ModScreenHandlers.STRIPPER_SCREEN_HANDLER,
                                StripperBlockScreenRenderer::new);
    }
}