package ru.mousecray.endmagic.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;

public class ServerPacketHandler implements ICustomPacketHandler.IServerPacketHandler {
    @Override
    public void handlePacket(PacketCustom packetCustom, EntityPlayerMP entityPlayerMP, INetHandlerPlayServer iNetHandlerPlayServer) {

    }
}
