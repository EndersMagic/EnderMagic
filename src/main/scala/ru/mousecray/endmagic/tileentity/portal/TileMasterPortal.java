package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.teleport.TeleportUtils;

import java.util.ArrayList;
import java.util.List;

public class TileMasterPortal extends TileWithLocation implements ITickable {
    private List<BlockPos> openedPortalPoses;

    public void openPortal(List<BlockPos> portalPoses) {
        openedPortalPoses = portalPoses;
        tick = 80;
    }


    public void updateDistination(Location readFromItem) {
        distination = readFromItem;
    }

    private int tick;

    @Override
    public void update() {
        if (openedPortalPoses != null) {
            tick--;
            if (tick <= 0)
                closePortal();
        }
    }

    protected void closePortal() {
        openedPortalPoses.forEach(world::setBlockToAir);
        openedPortalPoses = null;
    }

    public void onEntityCollidedWithPortal(Entity entity, BlockPos openedPortalPos) {
        TeleportUtils.teleportToBlockLocation(entity, distination);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound r = super.writeToNBT(compound);
        r.setInteger("tick", tick);
        if (openedPortalPoses != null)
            r.setTag("openedPortalPoses", toNbt(openedPortalPoses));
        return r;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        tick = compound.getInteger("tick");
        if (compound.hasKey("openedPortalPoses")) {
            openedPortalPoses = new ArrayList<>();
            NBTTagList list = compound.getTagList("openedPortalPoses", 4);
            for (int i = 0; i < list.tagCount(); i++)
                openedPortalPoses.add(BlockPos.fromLong(((NBTTagLong) list.get(i)).getLong()));
        }
    }

    private NBTTagList toNbt(List<BlockPos> openedPortalPoses) {
        NBTTagList r = new NBTTagList();
        openedPortalPoses.forEach(p -> r.appendTag(new NBTTagLong(p.toLong())));
        return r;
    }
}
