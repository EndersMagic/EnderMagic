package ru.mousecray.endmagic.gameobj.tileentity.portal;

import ru.mousecray.endmagic.util.teleport.Location;

public class TileMasterPortal extends TileWithLocation {

    public void updateDistination(Location readFromItem) {
        distination = readFromItem;
    }
}
