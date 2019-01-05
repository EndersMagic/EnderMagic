package ru.mousecray.endmagic.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEMEnderPearl extends EntityThrowable {
	
	protected ItemStack stack;
	protected EntityLivingBase entitylivingbase;
	protected EntityLivingBase entitylivingbase2;
	
	public EntityEMEnderPearl(World world) {
		super(world);
	}
	
    public EntityEMEnderPearl(World world, ItemStack stack) {
        super(world);
        this.stack = stack;
    }

    public EntityEMEnderPearl(World world, EntityLivingBase thrower, ItemStack stack) {
        super(world, thrower);
        this.stack = stack;
    }
    
    public EntityEMEnderPearl(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z);
        this.stack = stack;
    }
    
	@Override
    protected void onImpact(RayTraceResult result) {
		
        entitylivingbase = this.getThrower();
        entitylivingbase2 = null;
        
        if (result.entityHit != null) {
        	result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase), 2F);
            if(result.entityHit instanceof EntityLivingBase) entitylivingbase2 = ((EntityLivingBase)result.entityHit);
        }
        
        
        for (int i = 0; i < 32; ++i) this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());

        if (!this.world.isRemote) {
        	if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
        		for(int i = -2; i < 2; i++) {
        			for(int j = -2; j < 2; j++) {
		        		BlockPos blockpos = result.getBlockPos().add(i, 0, j);
//		        		if(world.getBlockState(blockpos).getBlock() instanceof BlockContainer)
		        		TileEntity tileentity = this.world.getTileEntity(blockpos);
		        		if(tileentity == null) {
			        		EntityFallingBlock entity = new EntityFallingBlock(world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), world.getBlockState(blockpos));
			        		entity.motionY = ((double)1/(rand.nextInt(5)+2));
			                entity.motionX = 0.0D;
			                entity.motionZ = 0.0D;
			                entity.moveToBlockPosAndAngles(blockpos, 0F, 0F);
			        		
			        		entity.setHurtEntities(true);
//		        			if(tileentity != null) entity.tileEntityData = tileentity.getTileData();
			        		world.spawnEntity(entity);
		        		}
        			}
        		}
        	}  
        }
    }
}