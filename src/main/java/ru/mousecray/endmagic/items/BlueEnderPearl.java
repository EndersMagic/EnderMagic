package ru.mousecray.endmagic.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;

public class BlueEnderPearl extends EMEnderPearl {

	public BlueEnderPearl() {
		setMaxStackSize(16);
	}
	
	@Override
	public void onImpact(EntityLivingBase result, EntityLivingBase thrower, EntityThrowable trowable) {
		double posX = result.posX;
		double posY = result.posY;
		double posZ = result.posZ;
		
		result.setPositionAndUpdate(thrower.posX, thrower.posY, thrower.posZ);
		result.fallDistance = 0.0F;
		result.attackEntityFrom(DamageSource.FALL, 5F);
		
		thrower.setPositionAndUpdate(posX, posY, posZ);
		thrower.fallDistance = 0.0F;
		thrower.attackEntityFrom(DamageSource.FALL, 5F);
	}

	@Override
	public String texture() {
		return "blue_ender_pearl";
	}
}