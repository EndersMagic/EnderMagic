package ru.mousecray.endmagic.gameobj.tileentity.portal;

import net.minecraft.util.ITickable;

public class TilePortal extends TileWithLocation implements ITickable {
    private int tick = 0;

    @Override
    public void update() {
        tick++;
        if (tick >= 80)
            world.setBlockToAir(pos);
    }
}
