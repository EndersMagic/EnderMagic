package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.entity.Entity;
import net.minecraft.util.ITickable;
import ru.mousecray.endmagic.teleport.TeleportUtils;

public class TilePortal extends TileWithLocation implements ITickable {
    private int tick = 0;

    @Override
    public void update() {
        tick++;
        if (tick >= 80)
            world.setBlockToAir(pos);
    }

    public void onEntityCollidedWithBlock(Entity entity) {
        TeleportUtils.teleportToBlockLocation(entity, distination);
    }
}
