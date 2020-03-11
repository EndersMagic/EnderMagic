package ru.mousecray.endmagic.entity;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import ru.mousecray.endmagic.items.EMEnderPearl;

public class EntityEMEnderPearl extends EntityThrowable {

	protected ItemStack itemStack = ItemStack.EMPTY;

	public EntityEMEnderPearl(World world) {
		super(world);
	}

	public EntityEMEnderPearl(World world, @Nonnull EntityLivingBase thrower, @Nonnull Item item) {
		super(world, thrower);
		this.itemStack = new ItemStack(item);
	}

	public EntityEMEnderPearl(World world, double x, double y, double z, @Nonnull Item item) {
		super(world, x, y, z);
		this.itemStack = new ItemStack(item);
	}

	@Override
	public void onUpdate() {
		EntityLivingBase entitylivingbase = this.getThrower();

		if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.isEntityAlive())
			setDead();
		else super.onUpdate();
	}

	@Override
	protected void onImpact(RayTraceResult result) {
        if (result.entityHit == thrower) return;
		if (result.typeOfHit != Type.MISS) {
			if (!world.isRemote) {
				if (result.entityHit instanceof EntityLivingBase) {
					EntityLivingBase resultEntity = (EntityLivingBase) result.entityHit;
					// result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this,
					// getThrower()), 2F);
					if (itemStack.getItem() instanceof EMEnderPearl) {
						EMEnderPearl item = (EMEnderPearl) this.itemStack.getItem();
						EntityLivingBase facticalThrower = getThrower();
						if (facticalThrower instanceof EntityPlayerMP) {
							EntityPlayerMP entityplayermp = (EntityPlayerMP) facticalThrower;

							if (entityplayermp.connection.getNetworkManager().isChannelOpen()
									&& entityplayermp.world == world && !entityplayermp.isPlayerSleeping()) {
								EnderTeleportEvent event = new EnderTeleportEvent(entityplayermp, posX, posY, posZ, 0F);
								if (!MinecraftForge.EVENT_BUS.post(event)) {
									spawnEndermite(result.entityHit, true);
									item.onImpact((EntityLivingBase) result.entityHit, facticalThrower, this);
								}
							}
						}
						else {
							spawnEndermite(result.entityHit, false);
							item.onImpact((EntityLivingBase) result.entityHit, facticalThrower, this);
						}
					}
				}
				
				if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
					for (int i = -2; i < 2; i++) {
						for (int j = -2; j < 2; j++) {
							BlockPos blockpos = result.getBlockPos().add(i, 0, j);
							TileEntity tileentity = this.world.getTileEntity(blockpos);
							if (tileentity == null) {
								EntityFallingBlock entity = new EntityFallingBlock(world, blockpos.getX(),
										blockpos.getY(), blockpos.getZ(), world.getBlockState(blockpos));
								entity.motionY = ((double) 1 / (rand.nextInt(5) + 2));
								entity.motionX = 0.0D;
								entity.motionZ = 0.0D;
								entity.moveToBlockPosAndAngles(blockpos, 0F, 0F);

								entity.setHurtEntities(true);
								world.spawnEntity(entity);
							}
						}
					}
					BlockPos blockpos = result.getBlockPos();
					for (int i = 0; i < 32; ++i)
						this.world.spawnParticle(EnumParticleTypes.PORTAL, blockpos.getX(), blockpos.getY() + rand.nextDouble() * 2.0D,
								blockpos.getZ(), rand.nextGaussian(), 0.0D, rand.nextGaussian());
				}
			}
		}
		setDead();
		world.setEntityState(this, (byte) 3);
	}

	protected void spawnEndermite(Entity entityHit, boolean spawnedByPlayer) {
		if (this.rand.nextFloat() < 0.05F && world.getGameRules().getBoolean("doMobSpawning")) {
			EntityEndermite entityendermite = new EntityEndermite(world);
			entityendermite.setSpawnedByPlayer(spawnedByPlayer);
			entityendermite.setLocationAndAngles(entityHit.posX, entityHit.posY, entityHit.posZ, entityHit.rotationYaw,
					entityHit.rotationPitch);
			world.spawnEntity(entityendermite);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		itemStack.writeToNBT(compound);
	}

	public ItemStack getCurrentItem() { return itemStack; }

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		itemStack = new ItemStack(compound);
	}
}