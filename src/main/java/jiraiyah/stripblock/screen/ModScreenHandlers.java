package jiraiyah.stripblock.screen;

import jiraiyah.stripblock.StripBlock;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers
{
    public static final ScreenHandlerType<StripperBlockScreenHandler> STRIPPER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(StripBlock.ModID, "stripper"),
                    new ExtendedScreenHandlerType<>(StripperBlockScreenHandler::new));

    public static void register()
    {
        StripBlock.LOGGER.info(">>> Registering Screen Handlers for : " + StripBlock.ModID);
    }
}