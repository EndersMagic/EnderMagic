package ru.mousecray.endmagic.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;

public class PurpleEnderPearl extends EMEnderPearl {
	
	@Override
	public void onImpact(EntityLivingBase result, EntityLivingBase thrower, EntityThrowable trowable) {
		result.setPositionAndUpdate(thrower.posX, thrower.posY, thrower.posZ);
		result.fallDistance = 0.0F;
		result.attackEntityFrom(DamageSource.FALL, 5F);
	}

    @Override
    public String texture() {
        return "purple_ender_pearl";
    }
}