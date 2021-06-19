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
import ru.mousecray.endmagic.tileentity.ByBlockNotifiable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.mousecray.endmagic.Configuration.portalOpenTime;
import static ru.mousecray.endmagic.network.PacketTypes.ADD_CHUNK_PORTAL_CAPA;
import static ru.mousecray.endmagic.network.PacketTypes.REMOVE_CHUNK_PORTAL_CAPA;

public abstract class TileMasterBasePortal extends TileWithLocation implements ITickable, ByBlockNotifiable {

    @Override
    public void neighborChanged() {
        if (state instanceof PortalState.Opened) {
            if (getkBottomCap() != ((PortalState.Opened) state).capMaterial)
                closePortal();
        } else if (world.isBlockPowered(pos))
            checkAndOpenPortal();
    }

    public void notifyTopCapUpdate(BlockPos pos) {
        if (state instanceof PortalState.Opened)
            if (!isTopCapValid(pos, ((PortalState.Opened) state).capMaterial))
                closePortal();
    }

    @Override
    public void breakBlock() {
        closePortal();
    }

    public interface PortalState {
        void tick(TileMasterBasePortal tile);

        PortalState nextState();

        class Opened implements PortalState {
            int tickOpened;
            AxisAlignedBB portalArea;
            Block capMaterial;

            Opened(int tickOpened, AxisAlignedBB portalArea, Block capMaterial) {
                this.tickOpened = tickOpened;
                this.portalArea = portalArea;
                this.capMaterial = capMaterial;
            }

            @Override
            public void tick(TileMasterBasePortal tile) {
                tickOpened--;
                tile.teleportEntities(tile.world.getEntitiesWithinAABBExcludingEntity(null, portalArea));
            }

            @Override
            public PortalState nextState() {
                if (tickOpened == 0)
                    return Closed;
                else
                    return this;
            }
        }

        PortalState Closed = new PortalState() {

            @Override
            public void tick(TileMasterBasePortal tile) {
            }

            @Override
            public PortalState nextState() {
                return this;
            }
        };
    }

    PortalState state = PortalState.Closed;

    public void checkAndOpenPortal() {
        if (destination != null) {
            checkStructure().ifPresent(p -> {
                int portalSpace = p.getLeft();
                Block capMaterial = p.getRight();
                if (checkDistinationStructure(portalSpace, capMaterial))
                    openPortal(portalSpace, capMaterial);
            });
        }
    }

    protected void openPortal(int portalSpace, Block capMaterial) {
        finalOpening(portalSpace, capMaterial);
    }

    protected void finalOpening(int portalSpace, Block capMaterial) {
        PortalCapabilityProvider.getPortalCapability(world.getChunkFromBlockCoords(pos)).masterPosToHeight.put(pos, portalSpace);
        ADD_CHUNK_PORTAL_CAPA.packet().writePos(pos).writeByte(portalSpace).sendPacketToAllAround(pos, 256, world.provider.getDimension());

        state = new PortalState.Opened(portalOpenTime, new AxisAlignedBB(pos.up()).expand(0, portalSpace - 1, 0), capMaterial);

        ((TileTopMark) world.getTileEntity(pos.up(portalSpace + 1))).masterTileOffset = portalSpace + 1;
    }

    protected void closePortal() {
        finalClosing();
    }

    protected void finalClosing() {
        PortalCapabilityProvider.getPortalCapability(world.getChunkFromBlockCoords(pos)).masterPosToHeight.remove(pos);
        REMOVE_CHUNK_PORTAL_CAPA.packet().writePos(pos).sendPacketToAllAround(pos, 256, world.provider.getDimension());

        state = PortalState.Closed;
    }

    protected boolean checkDistinationStructure(int portalSpace, Block capMaterial) {
        return destination.getBlockState().getBlock() == capMaterial;
    }

    protected Optional<Pair<Integer, Block>> checkStructure() {
        Block material = getkBottomCap();
        if (material != Blocks.AIR) {
            int height = 0;
            BlockPos current = pos.up();
            while (world.isAirBlock(current) && height < Configuration.portalSizeLimit) {
                height++;
                current = current.up();
            }

            if (world.getBlockState(current).getBlock() == EMBlocks.blockTopMark && isTopCapValid(current, material))
                return Optional.of(Pair.of(height, material));
            else
                return Optional.empty();
        } else
            return Optional.empty();
    }

    protected boolean isTopCapValid(BlockPos topPos, Block material) {
        Set<Block> collect =
                ImmutableList.of(topPos.up(), topPos.east(), topPos.south(), topPos.west(), topPos.north()).stream()
                        .map(p -> world.getBlockState(p).getBlock())
                        .collect(Collectors.toSet());
        return collect.size() == 1 && collect.iterator().next() == material;
    }

    protected Block getkBottomCap() {
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
            state.tick(this);
            state = state.nextState();
        }
    }

    protected abstract void teleportEntities(Collection<Entity> collidedEntities);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);


        String stateName = compound.getString("state");
        if (stateName.equals(PortalState.Opened.class.getSimpleName().toLowerCase())) {
            state = new PortalState.Opened(
                    compound.getInteger("tickOpened"),
                    new AxisAlignedBB(pos.up()).expand(0, compound.getInteger("height") - 1, 0),
                    Block.getBlockFromName(compound.getString("capMaterial")));

        } else if (stateName.equals(PortalState.Closed.class.getSimpleName().toLowerCase())) {
            state = new PortalState.Closed();
        } else
            throw new IllegalArgumentException(String.format("unsupported state in nbt \"%s\"", stateName));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("state", state.getClass().getSimpleName().toLowerCase());
        if (state instanceof PortalState.Opened) {
            PortalState.Opened state = (PortalState.Opened) this.state;
            compound.setInteger("tickOpened", state.tickOpened);
            compound.setInteger("height", (int) (state.portalArea.maxY - state.portalArea.minY));
            compound.setString("capMaterial", state.capMaterial.getRegistryName().toString());
        }
        return super.writeToNBT(compound);
    }
}
