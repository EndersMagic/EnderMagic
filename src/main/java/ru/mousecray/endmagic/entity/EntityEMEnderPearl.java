package ru.mousecray.endmagic.entity;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.items.EMEnderPearl;

public class EntityEMEnderPearl extends EntityThrowable {

	protected Item item = EMItems.purpleEnderPearl;

	public EntityEMEnderPearl(World world) {
		super(world);
	}

	public EntityEMEnderPearl(World world, @Nonnull EntityLivingBase thrower, @Nonnull Item item) {
		super(world, thrower);
		this.item = item;
	}

	public EntityEMEnderPearl(World world, double x, double y, double z, @Nonnull Item item) {
		super(world, x, y, z);
		this.item = item;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			if (result.entityHit instanceof EntityLivingBase) {
				EntityLivingBase resultEntity = (EntityLivingBase) result.entityHit;
				// result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this,
				// getThrower()), 2F);
				if (item instanceof EMEnderPearl) {
					EMEnderPearl item = (EMEnderPearl) this.item;
					if (!world.isRemote) {
						EntityLivingBase facticalThrower = getThrower();
						if (facticalThrower instanceof EntityPlayerMP) {
							EntityPlayerMP entityplayermp = (EntityPlayerMP) facticalThrower;

							if (entityplayermp.connection.getNetworkManager().isChannelOpen()
									&& entityplayermp.world == world && !entityplayermp.isPlayerSleeping()) {
								EnderTeleportEvent event = new EnderTeleportEvent(entityplayermp, posX, posY, posZ, 0F);
								if (!MinecraftForge.EVENT_BUS.post(event)) {
									spawnEndermite(result.entityHit, true);
									if (result.entityHit.isRiding()) { result.entityHit.dismountRidingEntity(); }
									item.onImpact((EntityLivingBase) result.entityHit, facticalThrower, this);
								}
							}
						}
						else {
							spawnEndermite(result.entityHit, false);
							if (result.entityHit.isRiding()) { result.entityHit.dismountRidingEntity(); }
							item.onImpact((EntityLivingBase) result.entityHit, facticalThrower, this);
						}
					}
				}
			}

			for (int i = 0; i < 32; ++i)
				this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D,
						this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());

			if (!world.isRemote) {
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
		compound.setString("item", item.getRegistryName().toString());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("item")) item = Item.getByNameOrId(compound.getString("item"));
	}
}