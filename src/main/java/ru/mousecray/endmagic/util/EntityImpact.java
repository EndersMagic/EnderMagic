package ru.mousecray.endmagic.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;

/**
 * Interface for different objects using the same EntityThrowable
 */
public interface EntityImpact {
	public void onImpact(EntityLivingBase result, EntityThrowable entity, EntityLivingBase thrower);
}
