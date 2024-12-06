package jiraiyah.wood_stripper.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;

public record StripDisplay(SlotDisplay ingredient,
                           SlotDisplay result,
                           SlotDisplay craftingStation,
                           int processTime)
        implements RecipeDisplay
{
    public static final MapCodec<StripDisplay> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            SlotDisplay.CODEC.fieldOf("ingredient").forGetter(StripDisplay::ingredient),
            SlotDisplay.CODEC.fieldOf("result").forGetter(StripDisplay::result),
            SlotDisplay.CODEC.fieldOf("craftingStation").forGetter(StripDisplay::craftingStation),
            Codec.INT.fieldOf("processTime").forGetter(StripDisplay::processTime)
    ).apply(inst, StripDisplay::new));

    public static final PacketCodec<RegistryByteBuf, StripDisplay> PACKET_CODEC = PacketCodec.tuple(
      SlotDisplay.PACKET_CODEC, StripDisplay::ingredient,
      SlotDisplay.PACKET_CODEC, StripDisplay::result,
      SlotDisplay.PACKET_CODEC, StripDisplay::craftingStation,
      PacketCodecs.INTEGER, StripDisplay::processTime,
      StripDisplay::new
    );

    public static final Serializer<StripDisplay> SERIALIZER = new Serializer<>(CODEC, PACKET_CODEC);

    @Override
    public SlotDisplay craftingStation()
    {
        return this.craftingStation;
    }

    @Override
    public Serializer<? extends RecipeDisplay> serializer()
    {
        return SERIALIZER;
    }
}