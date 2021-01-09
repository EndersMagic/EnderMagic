package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.entity.Entity;
import ru.mousecray.endmagic.util.teleport.TeleportationHelper;

import java.util.Collection;

public class TileMasterStaticPortal extends TileMasterBasePortal {
    @Override
    protected void teleportEntities(Collection<Entity> collidedEntities) {
        collidedEntities.forEach(e -> TeleportationHelper.teleportEntityAndPassengers(e, destination));
    }
}
