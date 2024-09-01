package jiraiyah.stripblock.screen;

import jiraiyah.stripblock.StripBlock;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static jiraiyah.stripblock.Reference.identifier;
import static jiraiyah.stripblock.Reference.logN;

public class ModScreenHandlers
{
    public static final ScreenHandlerType<StripperBlockScreenHandler> STRIPPER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, identifier("stripper"),
                    new ScreenHandlerType<>(StripperBlockScreenHandler::new,
                                            FeatureFlags.DEFAULT_ENABLED_FEATURES));

    public static void register()
    {
        logN(">>> Registering Screen Handlers");
    }
}