package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import ru.mousecray.endmagic.util.teleport.Location;
import ru.mousecray.endmagic.util.teleport.TeleportationHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TileMasterDarkPortal extends TileMasterBasePortal {

    @Override
    public void setDestination(Location destination) {
        super.setDestination(destination);
        Location currLocation = new Location(pos, world);
        getDestinationMasterTile().ifPresent(destinationMasterTile -> {
            if (!destinationMasterTile.getDestination().equals(currLocation)) {
                destinationMasterTile.setDestination(currLocation);
            }
        });
    }

    private Set<Entity> blacklist = new HashSet<>();

    @Override
    protected void openPortal(int portalSpace, Block capMaterial) {
        super.openPortal(portalSpace, capMaterial);
        getDestinationMasterTile().ifPresent(t -> t.finalOpening(portalSpace, capMaterial));
    }

    @Override
    protected void closePortal() {
        super.closePortal();
        getDestinationMasterTile().ifPresent(TileMasterDarkPortal::finalClosing);
    }

    @Override
    protected void finalClosing() {
        super.finalClosing();
        blacklist.clear();
    }

    @Override
    protected boolean checkDestinationStructure(int portalSpace, Block capMaterial) {
        return getDestinationMasterTile()
                .flatMap(TileMasterBasePortal::checkStructure)
                .filter(p -> p.getLeft() == portalSpace && p.getRight() == capMaterial)
                .isPresent();
    }

    @Override
    protected void teleportEntities(Collection<Entity> collidedEntities) {
        Optional<TileMasterDarkPortal> maybeTile = getDestinationMasterTile();
        if (maybeTile.isPresent()) {
            TileMasterDarkPortal destinationMasterTile = maybeTile.get();

            collidedEntities.forEach(e -> destinationMasterTile.blacklist.addAll(TeleportationHelper.getAllPassenges(e)));

            Vec3d currentMasterCenter = new Vec3d(pos).addVector(0.5, 0, 0.5);
            collidedEntities.forEach(e -> {
                if (!blacklist.contains(e)) {
                    Vec3d offset = e.getPositionVector().subtract(currentMasterCenter).rotateYaw((float) Math.toRadians(180));

                    TeleportationHelper.teleportEntityAndPassengers(e, getDestination().dim,
                            getDestination().x + offset.x + 0.5,
                            getDestination().y + offset.y,
                            getDestination().z + offset.z + 0.5);
                }
            });
        } else
            closePortal();
    }

    private Optional<TileMasterDarkPortal> getDestinationMasterTile() {
        TileEntity tile = getDestination().getWorld().getTileEntity(getDestination().toPos());
        return tile instanceof TileMasterDarkPortal ? Optional.of(((TileMasterDarkPortal) tile)) : Optional.empty();
    }
}
