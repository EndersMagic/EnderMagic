package ru.mousecray.endmagic.entity;

import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Triple;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMItems;

public class EntityEMEnderPearl extends EntityThrowable {

	protected ItemStack stack = new ItemStack(EMItems.purpleEnderPearl);
//	protected Consumer<Triple<EntityLivingBase, EntityLivingBase, EntityThrowable>> impacter;

	public EntityEMEnderPearl(World world) {
		super(world);
	}

	public EntityEMEnderPearl(World world, ItemStack stack) {
		super(world);
		this.stack = stack;
	}

	public EntityEMEnderPearl(World world, EntityLivingBase thrower,  ItemStack stack) {
		super(world, thrower);
		this.stack = stack;
	}

	public EntityEMEnderPearl(World world, double x, double y, double z,  ItemStack stack) {
		super(world, x, y, z);
		this.stack = stack;
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		if (result.entityHit != null) {
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 2F);
			if (result.entityHit instanceof EntityLivingBase) {
				if (stack != null && stack != ItemStack.EMPTY) {
					if (result.entityHit.isRiding()) { result.entityHit.dismountRidingEntity(); }
					
					stack.accept(Triple.of((EntityLivingBase) result.entityHit, getThrower(), this));
				}
				setDead();
				world.setEntityState(this, (byte) 3);
			}
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
        compound.setTag("Item", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("power", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("power", 6);

            if (nbttaglist.tagCount() == 3)
            {
                this.accelerationX = nbttaglist.getDoubleAt(0);
                this.accelerationY = nbttaglist.getDoubleAt(1);
                this.accelerationZ = nbttaglist.getDoubleAt(2);
            }
        }

        this.ticksAlive = compound.getInteger("life");

        if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3)
        {
            NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
            this.motionX = nbttaglist1.getDoubleAt(0);
            this.motionY = nbttaglist1.getDoubleAt(1);
            this.motionZ = nbttaglist1.getDoubleAt(2);
        }
        else
        {
            this.setDead();
        }
    }
}