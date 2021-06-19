package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import ru.mousecray.endmagic.util.teleport.TeleportationHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TileMasterDarkPortal extends TileMasterBasePortal {
    private Set<Entity> blacklist = new HashSet<>();

    @Override
    protected void openPortal(int portalSpace, Block capMaterial) {
        super.openPortal(portalSpace, capMaterial);
        ((TileMasterDarkPortal) getDestinationMasterTile()).finalOpening(portalSpace, capMaterial);
    }

    @Override
    protected void closePortal() {
        super.closePortal();
        TileEntity tileEntity = getDestinationMasterTile();
        if (tileEntity instanceof TileMasterDarkPortal)
            ((TileMasterDarkPortal) tileEntity).finalClosing();
    }

    @Override
    protected void finalClosing() {
        super.finalClosing();
        blacklist.clear();
    }

    @Override
    protected boolean checkDestinationStructure(int portalSpace, Block capMaterial) {
        TileEntity distinationTile = getDestinationMasterTile();
        return distinationTile instanceof TileMasterDarkPortal &&
                ((TileMasterDarkPortal) distinationTile).checkStructure()
                        .filter(p -> p.getLeft() == portalSpace && p.getRight() == capMaterial)
                        .isPresent();
    }

    @Override
    protected void teleportEntities(Collection<Entity> collidedEntities) {
        TileEntity destinationMasterTile = getDestinationMasterTile();
        if (destinationMasterTile instanceof TileMasterDarkPortal) {

            collidedEntities.forEach(e -> ((TileMasterDarkPortal) destinationMasterTile).blacklist.addAll(TeleportationHelper.getAllPassenges(e)));

            Vec3d currentMasterCenter = new Vec3d(pos).addVector(0.5, 0, 0.5);
            collidedEntities.forEach(e -> {
                if (!blacklist.contains(e)) {
                    Vec3d offset = e.getPositionVector().subtract(currentMasterCenter).rotateYaw((float) Math.toRadians(180));

                    TeleportationHelper.teleportEntityAndPassengers(e, destination.dim,
                            destination.x + offset.x + 0.5,
                            destination.y + offset.y,
                            destination.z + offset.z + 0.5);
                }
            });
        } else
            closePortal();
    }

    private TileEntity getDestinationMasterTile() {
        return destination.getWorld().getTileEntity(destination.toPos());
    }
}
