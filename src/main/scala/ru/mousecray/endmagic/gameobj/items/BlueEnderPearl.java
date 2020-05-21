package ru.mousecray.endmagic.gameobj.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

public class BlueEnderPearl extends EMEnderPearl {

    public BlueEnderPearl() {
        setMaxStackSize(16);
    }

    @Override
    public void onImpact(EntityLivingBase result, @Nullable EntityLivingBase thrower, EntityThrowable trowable) {
        double posX = result.posX;
        double posY = result.posY;
        double posZ = result.posZ;

        result.fallDistance = 0.0F;
        result.attackEntityFrom(DamageSource.FALL, 5F);
        if (result.isRiding()) result.dismountRidingEntity();

        if (thrower != null) {
            result.setPositionAndUpdate(thrower.posX, thrower.posY, thrower.posZ);
            if (thrower.isRiding()) thrower.dismountRidingEntity();
            thrower.setPositionAndUpdate(posX, posY, posZ);
            thrower.fallDistance = 0.0F;
            thrower.attackEntityFrom(DamageSource.FALL, 5F);
        }
    }

    @Override
    public String texture() {
        return "blue_ender_pearl";
    }
}