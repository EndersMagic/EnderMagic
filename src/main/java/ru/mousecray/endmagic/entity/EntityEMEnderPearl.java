package ru.mousecray.endmagic.entity;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.items.EMEnderPearl;

public class EntityEMEnderPearl extends EntityThrowable {

	protected Item item = EMItems.purpleEnderPearl;

	public EntityEMEnderPearl(World world) {
		super(world);
	}

	public EntityEMEnderPearl(World world, @Nonnull Item item) {
		super(world);
		this.item = item;
	}

	public EntityEMEnderPearl(World world, EntityLivingBase thrower, @Nonnull Item item) {
		super(world, thrower);
		this.item = item;
	}

	public EntityEMEnderPearl(World world, double x, double y, double z, @Nonnull Item item) {
		super(world, x, y, z);
		this.item = item;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null && result.entityHit instanceof EntityLivingBase) {
//			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 2F);
			if (item instanceof EMEnderPearl) {
				if (result.entityHit.isRiding()) { result.entityHit.dismountRidingEntity(); }
				((EMEnderPearl) item).onImpact((EntityLivingBase) result.entityHit, thrower, this);
			}
			setDead();
			world.setEntityState(this, (byte) 3);
		}

		for (int i = 0; i < 32; ++i)
			this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D,
					this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());

		if (!this.world.isRemote) {
			if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
				for (int i = -2; i < 2; i++) {
					for (int j = -2; j < 2; j++) {
						BlockPos blockpos = result.getBlockPos().add(i, 0, j);
						TileEntity tileentity = this.world.getTileEntity(blockpos);
						if (tileentity == null) {
							EntityFallingBlock entity = new EntityFallingBlock(world, blockpos.getX(), blockpos.getY(),
									blockpos.getZ(), world.getBlockState(blockpos));
							entity.motionY = ((double) 1 / (rand.nextInt(5) + 2));
							entity.motionX = 0.0D;
							entity.motionZ = 0.0D;
							entity.moveToBlockPosAndAngles(blockpos, 0F, 0F);

							entity.setHurtEntities(true);
							world.spawnEntity(entity);
						}
					}
				}
			}
		}
	}
	
    @Override
	public void writeEntityToNBT(NBTTagCompound compound) {
    	super.writeEntityToNBT(compound);
        compound.setString("item", item.getRegistryName().toString());
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound compound) {
    	super.readEntityFromNBT(compound);
        if (compound.hasKey("item")) item = Item.getByNameOrId(compound.getString("item"));
    }
}