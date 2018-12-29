package ru.mousecray.endmagic.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EMEnderPearl extends EntityThrowable {
    
    public EMEnderPearl(World world) {
        super(world);
    }

    public EMEnderPearl(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @SideOnly(Side.CLIENT)
    public EMEnderPearl(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) for (int i = 0; i < 8; ++i) this.world.spawnParticle(EnumParticleTypes.DRIP_LAVA, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }
    
	@Override
    protected void onImpact(RayTraceResult result) {
		
        EntityLivingBase entitylivingbase = this.getThrower();
        EntityLivingBase entitylivingbase2 = null;
        
        if (result.entityHit != null) {
        	result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2F);
            if(result.entityHit instanceof EntityLivingBase) entitylivingbase2 = ((EntityLivingBase)result.entityHit);
        }
        
        
        for (int i = 0; i < 32; ++i) this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());

        if (!this.world.isRemote) {
        	
        	if(entitylivingbase2 != null) {
        		
        		setDead();
        		
        		if (entitylivingbase2.isRiding()) {
        			entitylivingbase2.dismountRidingEntity();
        		}

        		entitylivingbase2.setPositionAndUpdate(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
        		entitylivingbase2.fallDistance = 0.0F;
        		entitylivingbase2.attackEntityFrom(DamageSource.FALL, 5F);
        	}
        	else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
        		setDead();
        		for(int i = -2; i < 2; i++) {
        			for(int j = -2; j < 2; j++) {
		        		BlockPos blockpos = result.getBlockPos().add(i, 0, j);
		        		TileEntity tileentity = this.world.getTileEntity(blockpos);
		        		EntityFallingBlock entity = new EntityFallingBlock(world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), world.getBlockState(blockpos));
		        		entity.motionY = ((double)1/(rand.nextInt(5)+2));
		                entity.motionX = 0.0D;
		                entity.motionZ = 0.0D;
		                entity.moveToBlockPosAndAngles(blockpos, 0F, 0F);
		        		
		        		entity.setHurtEntities(true);
		        		if(tileentity != null) entity.tileEntityData = tileentity.getTileData();
		        		world.spawnEntity(entity);
        				
//		        		BlockPos blockpos = result.getBlockPos().add(i, 0, j);
//		        		if(!(world.getBlockState(blockpos) instanceof BlockContainer)) {
//			        		EntityFallingBlock entity = new EntityFallingBlock(world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), world.getBlockState(blockpos));
//			        		entity.motionY = ((double)1/(rand.nextInt(5)+2));
//			                entity.motionX = 0.0D;
//			                entity.motionZ = 0.0D;
//			                entity.moveToBlockPosAndAngles(blockpos, 0F, 0F);
//			        		
//			        		entity.setHurtEntities(true);
//			        		world.spawnEntity(entity);
//		        		}
        			}
        		}
        	}  
        }
    }
}