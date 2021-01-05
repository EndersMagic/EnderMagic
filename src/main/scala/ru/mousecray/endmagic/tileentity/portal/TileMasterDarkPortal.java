package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import ru.mousecray.endmagic.teleport.TeleportationHelper;

import java.util.HashSet;
import java.util.Set;

import static ru.mousecray.endmagic.Configuration.portalOpenTime;

public class TileMasterDarkPortal extends TileMasterBasePortal {
    private Set<Entity> blacklist = new HashSet<>();

    public void addCollidedEntity(Entity entityIn) {
        if (!blacklist.contains(entityIn))
            super.addCollidedEntity(entityIn);
    }

    @Override
    protected void finalOpening(int portalSpace) {
        placePortalBlocks(portalSpace);
        tickOpened = portalOpenTime;
        TileMasterDarkPortal tileEntity = (TileMasterDarkPortal) destination.getWorld().getTileEntity(destination.toPos());
        tileEntity.placePortalBlocks(portalSpace);
        tileEntity.tickOpened = portalOpenTime;
    }

    @Override
    protected boolean checkDistinationStructure(int portalSpace, Block capMaterial) {
        TileEntity distinationTile = destination.getWorld().getTileEntity(destination.toPos());
        return distinationTile instanceof TileMasterDarkPortal &&
                ((TileMasterDarkPortal) distinationTile).checkStructure()
                        .filter(p -> p.getLeft() == portalSpace && p.getRight() == capMaterial)
                        .isPresent();
    }

    @Override
    protected void teleportEntities(Set<Entity> collidedEntities) {
        TileEntity destinationMasterTile = destination.getWorld().getTileEntity(destination.toPos());
        if (destinationMasterTile instanceof TileMasterDarkPortal) {
            collidedEntities.forEach(e -> ((TileMasterDarkPortal) destinationMasterTile).blacklist.addAll(TeleportationHelper.getAllPassenges(e)));

            Vec3d currentMasterCenter = new Vec3d(pos).addVector(0.5, 0, 0.5);
            collidedEntities.forEach(e -> {
                Vec3d offset = e.getPositionVector().subtract(currentMasterCenter).rotateYaw((float) Math.toRadians(180));

                TeleportationHelper.teleportEntityAndPassengers(e, destination.dim,
                        destination.x + offset.x + 0.5,
                        destination.y + offset.y,
                        destination.z + offset.z + 0.5);
            });
        } else
            tickOpened = 0;
    }
}
