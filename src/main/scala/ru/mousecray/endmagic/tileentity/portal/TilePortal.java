package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.tileentity.EMTileEntity;

import java.util.Optional;

public class TilePortal extends EMTileEntity {
    public BlockPos masterTilePos;

    public void onEntityCollidedWithBlock(Entity entity) {
        if (!world.isRemote)
            Optional.ofNullable(masterTilePos)
                    .map(pos -> world.getTileEntity(pos))
                    .filter(tile -> tile instanceof TileMasterPortal)
                    .ifPresent(tile -> ((TileMasterPortal) tile).onEntityCollidedWithPortal(entity, pos));

    }
}
