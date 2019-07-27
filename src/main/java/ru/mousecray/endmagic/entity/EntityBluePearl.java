package ru.mousecray.endmagic.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.EntityImpact;

public class EntityBluePearl extends EntityEMEnderPearl{

	public EntityBluePearl(World world) {
		super(world);
	}
	
    public EntityBluePearl(World world, ItemStack stack) {
        super(world, stack);
    }

    public EntityBluePearl(World world, EntityLivingBase thrower, ItemStack stack) {
        super(world, thrower, stack);
    }

    public EntityBluePearl(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

	@Override
	protected void onImpact(RayTraceResult result) {
		
		super.onImpact(result);
        
		if(!world.isRemote) {
	    	if(entitylivingbase2 != null) {
	    		//TODO: Revive the mine
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