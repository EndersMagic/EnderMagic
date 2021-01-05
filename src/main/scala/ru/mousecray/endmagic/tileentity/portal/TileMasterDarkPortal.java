package ru.mousecray.endmagic.tileentity.portal;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.Configuration;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.teleport.TeleportationHelper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.mousecray.endmagic.Configuration.portalOpenTime;

public class TileMasterDarkPortal extends TileWithLocation implements ITickable {
    private int tickOpened = -1;
    private Set<Entity> collidedEntities = new HashSet<>();
    private Set<Entity> blacklist = new HashSet<>();

    public void addCollidedEntity(Entity entityIn) {
        if (!blacklist.contains(entityIn))
            collidedEntities.add(entityIn);
    }

    public void openPortal() {
        if (distination != null) {
            checkStructure().ifPresent(p -> {
                int portalSpace = p.getLeft();
                Block capMaterial = p.getRight();
                if (checkDistinationStructure(portalSpace, capMaterial)) {
                    placePortalBlocks(portalSpace);
                    tickOpened = portalOpenTime;
                    ((TileMasterDarkPortal) distination.getWorld().getTileEntity(distination.toPos())).tickOpened = portalOpenTime;
                }
            });
        }
    }

    private void placePortalBlocks(int portalSpace) {
        for (int i = 1; i <= portalSpace; i++) {
            BlockPos current = pos.up(i);
            world.setBlockState(current, EMBlocks.blockPortal.getDefaultState());
            TilePortal tileEntity = (TilePortal) world.getTileEntity(current);
            tileEntity.masterTileOffset = i;
        }

        for (int i = 1; i <= portalSpace; i++) {
            BlockPos current = distination.toPos().up(i);
            distination.getWorld().setBlockState(current, EMBlocks.blockPortal.getDefaultState());
            TilePortal tileEntity = (TilePortal) distination.getWorld().getTileEntity(current);
            tileEntity.masterTileOffset = i;
        }
    }

    private boolean checkDistinationStructure(int portalSpace, Block capMaterial) {
        TileEntity distinationTile = distination.getWorld().getTileEntity(distination.toPos());
        return distinationTile instanceof TileMasterDarkPortal &&
                ((TileMasterDarkPortal) distinationTile).checkStructure()
                        .filter(p -> p.getLeft() == portalSpace && p.getRight() == capMaterial)
                        .isPresent();
    }

    private Optional<Pair<Integer, Block>> checkStructure() {
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

    private boolean checkTopCap(BlockPos topPos, Block material) {
        Set<Block> collect =
                ImmutableList.of(topPos.up(), topPos.east(), topPos.south(), topPos.west(), topPos.north()).stream()
                        .map(p -> world.getBlockState(p).getBlock())
                        .collect(Collectors.toSet());
        return collect.size() == 1 && collect.iterator().next() == material;
    }

    private Block checkBottomCap() {
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
            if (tickOpened > 0)
                tickOpened--;
            else if (tickOpened == 0)
                closePortal();

            if (collidedEntities.size() > 0) {
                TileEntity destinationMasterTile = distination.getWorld().getTileEntity(distination.toPos());
                if (destinationMasterTile instanceof TileMasterDarkPortal) {
                    collidedEntities.forEach(e -> ((TileMasterDarkPortal) destinationMasterTile).blacklist.addAll(TeleportationHelper.getAllPassenges(e)));

                    Vec3d currentMasterCenter = new Vec3d(pos).addVector(0.5, 0, 0.5);
                    collidedEntities.forEach(e -> {
                        Vec3d offset = e.getPositionVector().subtract(currentMasterCenter).rotateYaw((float) Math.toRadians(180));
                        
                        TeleportationHelper.teleportEntityAndPassengers(e, distination.dim,
                                distination.x + offset.x + 0.5,
                                distination.y + offset.y,
                                distination.z + offset.z + 0.5);
                    });
                    collidedEntities.clear();
                } else
                    tickOpened = 0;
            }
        }
    }

    private void closePortal() {
        tickOpened = -1;

        int height = 0;
        BlockPos current = pos.up();
        while (world.getBlockState(current).getBlock() == EMBlocks.blockPortal && height < Configuration.portalSizeLimit) {
            world.setBlockToAir(current);
            height++;
            current = current.up();
        }
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
