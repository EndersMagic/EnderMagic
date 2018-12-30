package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.entity.Entity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import ru.mousecray.endmagic.teleport.TeleportUtils;

import java.util.List;

public class TilePortal extends TileWithLocation implements ITickable {
    private int tick = 0;

    @Override
    public void update() {
        tick++;
        if (tick >= 80)
            world.setBlockToAir(pos);
    }
}
