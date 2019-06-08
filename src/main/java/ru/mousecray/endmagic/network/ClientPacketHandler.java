package ru.mousecray.endmagic.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import ru.mousecray.endmagic.client.render.model.baked.FinalisedModelEnderCompass;

public class ClientPacketHandler implements ICustomPacketHandler.IClientPacketHandler {
    public static final int UPDATE_COMPAS_TARGET = 1;

    @Override
    public void handlePacket(PacketCustom packetCustom, Minecraft minecraft, INetHandlerPlayClient iNetHandlerPlayClient) {
        switch (packetCustom.getType()) {
            case UPDATE_COMPAS_TARGET:
                FinalisedModelEnderCompass.target.put(packetCustom.readInt(), packetCustom.readPos());
                break;
            default:
                break;
        }
    }
}
