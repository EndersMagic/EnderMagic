package ru.mousecray.endmagic.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.capability.chunk.portal.PortalCapability;
import ru.mousecray.endmagic.capability.chunk.portal.PortalCapabilityProvider;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroup;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapability;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapabilityProvider;
import ru.mousecray.endmagic.client.render.model.baked.FinalisedModelEnderCompass;

public class ClientPacketHandler implements ICustomPacketHandler.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packetCustom, Minecraft minecraft, INetHandlerPlayClient iNetHandlerPlayClient) {
        switch (PacketTypes.valueOf(packetCustom.getType())) {
            case UPDATE_COMPAS_TARGET:
                FinalisedModelEnderCompass.target.put(packetCustom.readInt(), packetCustom.readPos());
                break;
            case UPDATE_PHANROM_AVOIDINCAPABILITY:
                WorldClient world = Minecraft.getMinecraft().world;
                if (packetCustom.readInt() == world.provider.getDimension()) {
                    PhantomAvoidingGroupCapability capability = world.getCapability(PhantomAvoidingGroupCapabilityProvider.avoidingGroupCapability, null);
                    if (capability != null) {

                        PhantomAvoidingGroup newGroup = new PhantomAvoidingGroup();
                        newGroup.avoidingStarted = packetCustom.readBoolean();
                        newGroup.avoidTicks = packetCustom.readInt();
                        newGroup.increment = packetCustom.readInt();

                        int size = packetCustom.readInt();
                        for (int i = 0; i < size; i++)
                            newGroup.blocks.add(packetCustom.readPos());

                        Minecraft.getMinecraft().addScheduledTask(() -> capability.forAdded.add(newGroup));
                    }
                }
                break;
            case SYNC_CHUNK_PORTAL_CAPA: {
                PortalCapability capability = PortalCapabilityProvider.getPortalCapability(Minecraft.getMinecraft().world.getChunkFromChunkCoords(packetCustom.readInt(), packetCustom.readInt()));
                if (capability != null)
                    PortalCapabilityProvider.portalCapability.readNBT(capability, EnumFacing.UP, packetCustom.readNBTTagCompound().getTag("value"));

            }
            break;
            case ADD_CHUNK_PORTAL_CAPA: {
                BlockPos pos = packetCustom.readPos();
                PortalCapability capability = PortalCapabilityProvider.getPortalCapability(Minecraft.getMinecraft().world.getChunkFromBlockCoords(pos));
                if (capability != null)
                    capability.masterPosToHeight.put(pos, (int) packetCustom.readByte());
            }
            break;
            case REMOVE_CHUNK_PORTAL_CAPA: {
                BlockPos pos = packetCustom.readPos();
                PortalCapability capability = PortalCapabilityProvider.getPortalCapability(Minecraft.getMinecraft().world.getChunkFromBlockCoords(pos));
                if (capability != null)
                    capability.masterPosToHeight.remove(pos);
            }
            break;
            default:
                break;
        }
    }
}
