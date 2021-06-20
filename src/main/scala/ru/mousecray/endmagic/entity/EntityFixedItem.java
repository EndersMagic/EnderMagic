package ru.mousecray.endmagic.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityFixedItem extends EntityItem {
    public EntityFixedItem(World worldIn, double x, double y, double z, EntityItem base) {
        super(worldIn, x, y, z, base.getItem().copy());
        init();
        hoverStart = base.hoverStart;
        rotationYaw = base.rotationYaw;
    }

    public EntityFixedItem(World worldIn) {
        super(worldIn);
        init();
    }

    public void init() {
        setNoDespawn();
        setPickupDelay(0);
        motionX = 0;
        motionY = 0;
        motionZ = 0;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        posX = prevPosX;
        posY = prevPosY;
        posZ = prevPosZ;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (entityIn.isSneaking() && Math.abs(entityIn.posY - posY) < 0.5)
            super.onCollideWithPlayer(entityIn);
    }
}
