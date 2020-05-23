package ru.mousecray.endmagic.gameobj.tileentity.portal;

import ru.mousecray.endmagic.util.teleport.Location;

import java.util.Optional;

public class TileMasterDarkPortal extends TileMasterPortal {
    @Override
    public void updateDistination(Location readFromItem) {
        Optional.ofNullable(readFromItem.getWorld().getTileEntity(readFromItem.toPos()))
                .filter(tile -> tile instanceof TileMasterDarkPortal)
                .ifPresent(tile -> ((TileMasterDarkPortal) tile).distination = new Location(pos, world));
        super.updateDistination(readFromItem);
    }
}
