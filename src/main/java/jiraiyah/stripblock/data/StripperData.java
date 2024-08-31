package jiraiyah.stripblock.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;

public record StripperData(BlockPos pos)
{
    public static final PacketCodec<ByteBuf, StripperData> PACKET_CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC,
                    StripperData::pos,
                    StripperData::new);
}