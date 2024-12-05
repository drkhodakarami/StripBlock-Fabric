package jiraiyah.wood_stripper.registry;

import jiraiyah.jiralib.record.BlockPosPayload;
import jiraiyah.register.Registers;
import jiraiyah.wood_stripper.screen.StripperBlockScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import static jiraiyah.wood_stripper.Main.*;

public class ModScreenHandlers
{
    public static ScreenHandlerType<StripperBlockScreenHandler> STRIPPER_SCREEN_HANDLER;

    public static void init()
    {
        LOGGER.log("Registering Screen Handlers");

        Registers.init(ModID);

        STRIPPER_SCREEN_HANDLER = register("wood_stripper_screen", StripperBlockScreenHandler::new,
                                           BlockPosPayload.PACKET_CODEC);
    }

    public static <T extends ScreenHandler, D extends CustomPayload> ExtendedScreenHandlerType<T, D>
    register(String name,
             ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
             PacketCodec<? super RegistryByteBuf, D> codec)
    {
        var key = Registers.getKey(name, RegistryKeys.SCREEN_HANDLER);
        return Registry.register(Registries.SCREEN_HANDLER, key,
                                 new ExtendedScreenHandlerType<>(factory, codec));
    }
}