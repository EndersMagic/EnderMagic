package ru.mousecray.endmagic.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroup;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapability;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapabilityProvider;
import ru.mousecray.endmagic.client.render.model.baked.FinalisedModelEnderCompass;

public class ServerPacketHandler implements ICustomPacketHandler.IServerPacketHandler
{
    @Override
    public void handlePacket(PacketCustom packetCustom, EntityPlayerMP entityPlayerMP, INetHandlerPlayServer iNetHandlerPlayServer)
    {
        switch (PacketTypes.valueOf(packetCustom.getType()))
        {
            case CHANDE_DEMENSION:
                World world = entityPlayerMP.getServerWorld();
                if (entityPlayerMP.dimension == 0) entityPlayerMP.changeDimension(1);
                else if (entityPlayerMP.dimension == 1) entityPlayerMP.changeDimension(0);
                break;
            default:
                break;
            case STRUCTURE_FIND:
                /*//to structure find todo
                World world2 = entityPlayerMP.getServerWorld();
                BlockPos pos = world2.findNearestStructure(packetCustom.readString(), entityPlayerMP.getPosition(), true);
                entityPlayerMP.attemptTeleport(pos.getX(), world2.getHeight(pos.getX(), pos.getZ()), pos.getZ());*/
                break;
        }
    }
}
