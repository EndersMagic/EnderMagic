package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.teleport.TeleportUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class TileMasterDarkPortal extends TileMasterPortal {
    @Override
    public void updateDistination(Location readFromItem) {
        Optional.ofNullable(readFromItem.getWorld().getTileEntity(readFromItem.toPos()))
                .filter(tile -> tile instanceof TileMasterDarkPortal)
                .ifPresent(tile -> ((TileMasterDarkPortal) tile).distination = new Location(pos, world));
        super.updateDistination(readFromItem);
    }

    private Set<Entity> outerFilter = new HashSet<>();

    private void addEntityToFilter(Entity entity) {
        getAllRidingEntities(entity.getLowestRidingEntity()).forEachOrdered(outerFilter::add);
    }

    private Stream<Entity> getAllRidingEntities(Entity rootEntity) {
        return Stream.concat(Stream.of(rootEntity),rootEntity.getPassengers().stream().flatMap(this::getAllRidingEntities));
    }

    private boolean canUsePortal(Entity entity) {
        return !outerFilter.contains(entity);
    }

    @Override
    protected void closePortal() {
        super.closePortal();
        outerFilter.clear();
    }

    @Override
    public void onEntityCollidedWithPortal(Entity entity, BlockPos openedPortalPos) {
        if (canUsePortal(entity)) {
            TileMasterDarkPortal targetMasterTile = (TileMasterDarkPortal) distination.getWorld().getTileEntity(distination.toPos());
            if (targetMasterTile != null) {
                targetMasterTile.addEntityToFilter(entity);
                addEntityToFilter(entity);

                BlockPos offset = openedPortalPos.subtract(pos);
                Vec3d entityOffset = entity.getPositionVector().subtract(openedPortalPos.getX() + 0.5, openedPortalPos.getY() + 0.5, openedPortalPos.getZ() + 0.5)
                        .rotateYaw((float) Math.toRadians(180));
                Minecraft.getMinecraft().addScheduledTask(() -> TeleportUtils.teleportEntity(entity, distination.dim,
                        distination.x + offset.getX() + entityOffset.x + 0.5,
                        distination.y + offset.getY() + entityOffset.y + 1,
                        distination.z + offset.getZ() + entityOffset.z + 0.5));
            }
        }
    }
}
