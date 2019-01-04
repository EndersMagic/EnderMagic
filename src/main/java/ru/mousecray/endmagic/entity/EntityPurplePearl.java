package ru.mousecray.endmagic.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.EntityImpact;

public class EntityPurplePearl extends EntityEMEnderPearl {

	public EntityPurplePearl(World world) {
		super(world);
	}
	
    public EntityPurplePearl(World world, ItemStack stack) {
        super(world, stack);
    }

    public EntityPurplePearl(World world, EntityLivingBase thrower, ItemStack stack) {
        super(world, thrower, stack);
    }

    public EntityPurplePearl(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

	@Override
	protected void onImpact(RayTraceResult result) {
		
		super.onImpact(result);
        
		if(!world.isRemote) {
	    	if(entitylivingbase2 != null) {
	    		if(stack != ItemStack.EMPTY && stack.getItem() instanceof EntityImpact) {
	    			
	    			if (entitylivingbase2.isRiding()) {
	    				entitylivingbase2.dismountRidingEntity();
	    			}
	    			
	    			((EntityImpact)stack.getItem()).onImpact(entitylivingbase2, this, entitylivingbase);
	    		}
	    	}
	    	setDead();
	    	world.setEntityState(this, (byte)3);
		}
		
	}
}