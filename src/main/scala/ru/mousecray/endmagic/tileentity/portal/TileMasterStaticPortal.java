package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.entity.Entity;
import ru.mousecray.endmagic.util.teleport.TeleportationHelper;

import java.util.Set;

import static ru.mousecray.endmagic.Configuration.portalOpenTime;

public class TileMasterStaticPortal extends TileMasterBasePortal {

    @Override
    protected void finalOpening(int portalSpace) {
        placePortalBlocks(portalSpace);
        tickOpened = portalOpenTime;
    }

    @Override
    protected void teleportEntities(Set<Entity> collidedEntities) {
        collidedEntities.forEach(e -> TeleportationHelper.teleportEntityAndPassengers(e, destination));
    }
}
