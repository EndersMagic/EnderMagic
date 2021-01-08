package ru.mousecray.endmagic.tileentity.portal;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.Configuration;
import ru.mousecray.endmagic.capability.chunk.portal.PortalCapabilityProvider;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.mousecray.endmagic.Configuration.portalOpenTime;
import static ru.mousecray.endmagic.network.PacketTypes.ADD_CHUNK_PORTAL_CAPA;
import static ru.mousecray.endmagic.network.PacketTypes.REMOVE_CHUNK_PORTAL_CAPA;

public abstract class TileMasterBasePortal extends TileWithLocation implements ITickable {
    int tickOpened = -1;
    int height = 0;

    public void openPortal() {
        if (destination != null) {
            checkStructure().ifPresent(p -> {
                int portalSpace = p.getLeft();
                Block capMaterial = p.getRight();
                if (checkDistinationStructure(portalSpace, capMaterial))
                    finalOpening(portalSpace);
            });
        }
    }

    protected abstract void finalOpening(int portalSpace);

    protected void placePortalBlocks(int portalSpace) {
        PortalCapabilityProvider.getPortalCapability(world.getChunkFromBlockCoords(pos)).masterPosToHeight.put(pos, portalSpace);
        ADD_CHUNK_PORTAL_CAPA.packet().writePos(pos).writeByte(portalSpace).sendPacketToAllAround(pos, 256, world.provider.getDimension());
        height = portalSpace;
        tickOpened = portalOpenTime;
    }

    protected boolean checkDistinationStructure(int portalSpace, Block capMaterial) {
        return destination.getBlockState().getBlock() == capMaterial;
    }

    protected Optional<Pair<Integer, Block>> checkStructure() {
        Block material = checkBottomCap();
        if (material != Blocks.AIR) {
            int height = 0;
            BlockPos current = pos.up();
            while (world.isAirBlock(current) && height < Configuration.portalSizeLimit) {
                height++;
                current = current.up();
            }

            if (world.getBlockState(current).getBlock() == EMBlocks.blockTopMark && checkTopCap(current, material))
                return Optional.of(Pair.of(height, material));
            else
                return Optional.empty();
        } else
            return Optional.empty();
    }

    protected boolean checkTopCap(BlockPos topPos, Block material) {
        Set<Block> collect =
                ImmutableList.of(topPos.up(), topPos.east(), topPos.south(), topPos.west(), topPos.north()).stream()
                        .map(p -> world.getBlockState(p).getBlock())
                        .collect(Collectors.toSet());
        return collect.size() == 1 && collect.iterator().next() == material;
    }

    protected Block checkBottomCap() {
        Set<Block> collect =
                ImmutableList.of(pos.down(), pos.east(), pos.south(), pos.west(), pos.north()).stream()
                        .map(p -> world.getBlockState(p).getBlock())
                        .collect(Collectors.toSet());
        if (collect.size() == 1)
            return collect.iterator().next();
        else
            return Blocks.AIR;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (tickOpened > 0) {
                tickOpened--;
                AxisAlignedBB portalArea = new AxisAlignedBB(pos).expand(0, height - 1, 0);
                teleportEntities(world.getEntitiesWithinAABBExcludingEntity(null, portalArea));
            } else if (tickOpened == 0)
                closePortal();
        }
    }

    protected abstract void teleportEntities(Collection<Entity> collidedEntities);

    private void closePortal() {
        tickOpened = -1;
        height = 0;
        PortalCapabilityProvider.getPortalCapability(world.getChunkFromBlockCoords(pos)).masterPosToHeight.remove(pos);
        REMOVE_CHUNK_PORTAL_CAPA.packet().writePos(pos).sendPacketToAllAround(pos, 256, world.provider.getDimension());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        tickOpened = compound.getInteger("tickOpened");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("tickOpened", tickOpened);
        return super.writeToNBT(compound);
    }
}
