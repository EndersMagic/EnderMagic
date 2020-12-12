package ru.mousecray.endmagic.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.chunk.IRuneChunkCapability;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.capability.player.ClientEmCapability;
import ru.mousecray.endmagic.capability.player.EmCapability;
import ru.mousecray.endmagic.capability.player.EmCapabilityProvider;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroup;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapability;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapabilityProvider;
import ru.mousecray.endmagic.client.render.model.baked.FinalisedModelEnderCompass;
import ru.mousecray.endmagic.rune.RuneColor;
import ru.mousecray.endmagic.rune.RuneIndex;
import ru.mousecray.endmagic.util.Vec2i;

import static ru.mousecray.endmagic.capability.chunk.RuneStateCapabilityProvider.runeStateCapability;

public class ClientPacketHandler implements ICustomPacketHandler.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packetCustom, Minecraft minecraft, INetHandlerPlayClient iNetHandlerPlayClient) {
        switch (PacketTypes.valueOf(packetCustom.getType())) {
            case UPDATE_COMPAS_TARGET: {
                FinalisedModelEnderCompass.target.put(packetCustom.readInt(), packetCustom.readPos());
                break;
            }
            case UPDATE_PHANROM_AVOIDINCAPABILITY: {
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
            }
            case SYNC_RUNE_CAPABILITY: {
                Chunk chunk = minecraft.world.getChunkFromChunkCoords(packetCustom.readInt(), packetCustom.readInt());
                IRuneChunkCapability capability = RuneIndex.getCapability(chunk);
                runeStateCapability.readNBT(capability, EnumFacing.UP, packetCustom.readNBTTagCompound());
                EM.proxy.refreshChunk(minecraft.world, capability.existingRunes().isEmpty() ? new BlockPos(chunk.x << 4, 0, chunk.z << 4) : capability.existingRunes().keySet().iterator().next());

                break;
            }
            case ADDED_RUNE_PART: {
                RuneIndex.addRunePart(minecraft.world, packetCustom.readPos(), packetCustom.readEnumFacing(), new Vec2i(packetCustom.readInt(), packetCustom.readInt()), new RunePart(packetCustom.readByte()));
                break;
            }
            case REMOVE_RUNE_STATE: {
                RuneIndex.removeRune(minecraft.world, packetCustom.readPos());
                break;
            }
            case UPDATE_PLAYER_EM: {
                EmCapability capa = EmCapabilityProvider.getCapa(minecraft.player);
                RuneColor color = RuneColor.values()[packetCustom.readByte()];
                capa.setEm(color, packetCustom.readInt());
                capa.setMaxEm(color, packetCustom.readInt());
                break;
            }
            case SYNC_PLAYER_EM_CAPABILITY: {
                EmCapability capa = EmCapabilityProvider.getCapa(minecraft.player);
                for (RuneColor color : RuneColor.values()) {
                    capa.setEm(color, packetCustom.readInt());
                    capa.setMaxEm(color, packetCustom.readInt());
                }
                ((ClientEmCapability) capa).dismissPrevs();
                break;
            }
            default:
                break;
        }
    }
}
