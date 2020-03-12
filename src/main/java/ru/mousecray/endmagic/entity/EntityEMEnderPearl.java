package ru.mousecray.endmagic.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.items.EMEnderPearl;

public class EntityEMEnderPearl extends EntityThrowable {

	private static final DataParameter<ItemStack> itemStack = EntityDataManager
			.<ItemStack>createKey(EntityEMEnderPearl.class, DataSerializers.ITEM_STACK);
	private EntityLivingBase perlThrower;

	public EntityEMEnderPearl(World world) {
		super(world);
	}

	public EntityEMEnderPearl(World world, EntityLivingBase thrower, ItemStack itemStack) {
		super(world, thrower);
		this.perlThrower = thrower;
		setItemStack(itemStack);
	}

	public EntityEMEnderPearl(World world, double x, double y, double z, ItemStack itemStack) {
		super(world, x, y, z);
		if (!itemStack.isEmpty()) { setItemStack(itemStack); }
	}

	public void setItemStack(ItemStack stack) {
		getDataManager().set(itemStack, stack.copy());
		getDataManager().setDirty(itemStack);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(itemStack, ItemStack.EMPTY);
	}

	@Override
	public void onUpdate() {
		EntityLivingBase entitylivingbase = getThrower();

		if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.isEntityAlive()) this.setDead();
		else super.onUpdate();
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit == this.perlThrower) return;
        
		if (!world.isRemote) {
			if (result.entityHit instanceof EntityLivingBase) {
				EntityLivingBase resultEntity = (EntityLivingBase) result.entityHit;
				ItemStack stack = getItemStack();
				EMEnderPearl item = (EMEnderPearl) stack.getItem();
				EntityLivingBase facticalThrower = getThrower();
				if (facticalThrower instanceof EntityPlayerMP) {
					EntityPlayerMP entityplayermp = (EntityPlayerMP) facticalThrower;

					if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == world
							&& !entityplayermp.isPlayerSleeping()) {
						EnderTeleportEvent event = new EnderTeleportEvent(resultEntity, facticalThrower.posX, facticalThrower.posY, facticalThrower.posZ, 0F);
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
				BlockPos blockpos = result.getBlockPos();
				for (int i = 0; i < 32; ++i) this.world.spawnParticle(EnumParticleTypes.PORTAL, blockpos.getX(),
						blockpos.getY() + rand.nextDouble() * 2.0D, blockpos.getZ(), rand.nextGaussian(), 0.0D,
						rand.nextGaussian());
			}
		}
		world.setEntityState(this, (byte) 3);
		setDead();
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
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		ItemStack itemstack = new ItemStack(compound.getCompoundTag("ItemStack"));

		if (itemstack.isEmpty()) setDead();
		else setItemStack(itemstack);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		ItemStack itemstack = getItemStack();

		if (!itemstack.isEmpty()) { compound.setTag("ItemStack", itemstack.writeToNBT(new NBTTagCompound())); }
	}

	public ItemStack getItemStack() {
		ItemStack itemstack = (ItemStack) this.getDataManager().get(itemStack);
		if (!(itemstack.getItem() instanceof EMEnderPearl)) return new ItemStack(EMItems.purpleEnderPearl);
		else return itemstack;
	}

	@Override
	@Nullable
	public Entity changeDimension(int dimension, ITeleporter teleporter) {
		if (thrower != null && thrower.dimension != dimension) thrower = null;
		return super.changeDimension(dimension, teleporter);
	}
}