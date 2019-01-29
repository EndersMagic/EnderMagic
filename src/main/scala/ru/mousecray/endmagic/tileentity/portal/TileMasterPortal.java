package ru.mousecray.endmagic.tileentity.portal;

import ru.mousecray.endmagic.teleport.Location;

public class TileMasterPortal extends TileWithLocation {

    public void updateDistination(Location readFromItem) {
        distination = readFromItem;
    }
}
