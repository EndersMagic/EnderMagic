package ru.mousecray.endmagic.capability.chunk.portal;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static ru.mousecray.endmagic.network.PacketTypes.SYNC_CHUNK_PORTAL_CAPA;

@Mod.EventBusSubscriber(modid = EM.ID)
public class PortalCapabilityProvider implements ICapabilitySerializable {

    public static PortalCapability getPortalCapability(Chunk chunk) {
        return chunk.getCapability(portalCapability, EnumFacing.UP);
    }


    @SubscribeEvent
    public static void onCapaAttachToChunk(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(PortalCapabilityProvider.name, new PortalCapabilityProvider(event.getObject()));
    }

    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent event) {
        NBTTagCompound r = new NBTTagCompound();
        r.setTag("value", portalCapability.writeNBT(getPortalCapability(event.getChunkInstance()), EnumFacing.UP));
        SYNC_CHUNK_PORTAL_CAPA.packet()
                .writeInt(event.getChunkInstance().x)
                .writeInt(event.getChunkInstance().z)
                .writeNBTTagCompound(r)
                .sendToPlayer(event.getPlayer());
    }

    @CapabilityInject(PortalCapability.class)
    public static Capability<PortalCapability> portalCapability;
    public static ResourceLocation name = new ResourceLocation(EM.ID, NameAndTabUtils.getName(PortalCapability.class));

    PortalCapability value;

    public PortalCapabilityProvider(Chunk chunk) {
        value = new PortalCapability(chunk);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == portalCapability && facing == EnumFacing.UP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == portalCapability && facing == EnumFacing.UP ? portalCapability.cast(value) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return portalCapability.writeNBT(value, EnumFacing.UP);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        portalCapability.readNBT(value, EnumFacing.UP, nbt);
    }
}
