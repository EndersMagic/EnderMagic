package ru.mousecray.endmagic.entity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.init.EMBlocks;

public class EntityCurseBush extends EntityMob {
	
	protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.<BlockPos>createKey(EntityCurseBush.class, DataSerializers.BLOCK_POS);
	
	private boolean isInvade;
	private boolean isSleep;
	private int sleepTime;
	private boolean enableEffect;
	
	public EntityCurseBush(World world) {
		super(world);
		setJumping(false);
		setOrigin(new BlockPos(this));
		setSize(0.98F, 0.98F);
		sleepTime = 0;
		isInvade = false;
		isSleep = false;
		enableEffect = false;
	}
	
	public EntityCurseBush(World world, BlockPos pos) {
		this(world);
		setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(ORIGIN, BlockPos.ORIGIN);
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
		tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		tasks.addTask(5, new EntityAISpawnAreaEffects(this));
		tasks.addTask(6, new EntityAITransformToBlock(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.5D);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(getAttackTarget() != null) {		
			isInvade = true;
			getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		}
		else {
			isInvade = false;
			getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
		}
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("isInvade", isInvade);
		compound.setBoolean("isSleep", isSleep);
		compound.setInteger("sleepTime", sleepTime);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		isInvade = compound.getBoolean("isInvade");
		isSleep = compound.getBoolean("isSleep");
		sleepTime = compound.getInteger("sleepTime");
	}

	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
			player.swingArm(hand);
			if (!this.world.isRemote) {
				setFire(3);
				itemstack.damageItem(1, player);
				return true;
			}
		}
		else if (itemstack.getItem() == Items.SHEARS) {
			NBTTagList enchantments = itemstack.getEnchantmentTagList();
			for (int i = 0; i < enchantments.tagCount(); ++i) {
				NBTTagCompound tag = enchantments.getCompoundTagAt(i);
				Enchantment itemEnchant = Enchantment.getEnchantmentByID(tag.getShort("id"));
				if (itemEnchant == Enchantments.SILK_TOUCH)  {
					damageEntity(DamageSource.MAGIC, 10.0F);
					itemstack.damageItem(5, player);
					return true;
				}
			}
		}

		return processInteract(player, hand);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithNearbyEntities() {}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		transformToBlock();
		super.fall(distance, damageMultiplier);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.isFireDamage()) setFire(5);
		return (source == DamageSource.FALLING_BLOCK || source == DamageSource.FLY_INTO_WALL || 
				source == DamageSource.OUT_OF_WORLD) ? super.attackEntityFrom(source, amount) : false;
	}

	public void transformToBlock() {
		setDead();
		world.setBlockState(getPosition(), EMBlocks.blockCurseBush.getDefaultState());
	}

	@Override
    public void onDeath(DamageSource cause) {
    	super.onDeath(cause);
    	if(cause.isFireDamage()) InventoryHelper.spawnItemStack(world, posX, posY, posZ, new ItemStack(Items.COAL));
    }

	public boolean isInvade() {
		return isInvade;
	}

	public void setInvade(boolean isInvade) {
		this.isInvade = isInvade;
	}

	public boolean isSleep() {
		return isSleep;
	}

	public void setSleep(boolean isSleep) {
		this.isSleep = isSleep;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getSleepTime() {
		return sleepTime;
	}
	
	public boolean isEnableEffect() {
		return enableEffect;
	}
	
	public void setEnableEffect(boolean enableEffect) {
		this.enableEffect = enableEffect;
	}

	@Override
	public boolean canBeHitWithPotion() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public World getWorldObj() {
		return this.world;
	}

	@SideOnly(Side.CLIENT)
	public BlockPos getOrigin() {
		return (BlockPos) this.dataManager.get(ORIGIN);
	}

	public void setOrigin(BlockPos pos) {
		this.dataManager.set(ORIGIN, pos);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.BLOCK_GRASS_HIT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.BLOCK_GRASS_STEP;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_GRASS_FALL;
	}

	public class EntityAITransformToBlock extends EntityAIBase {

		private final EntityCurseBush bush;

		public EntityAITransformToBlock(EntityCurseBush bush) {
			this.bush = bush;
			setMutexBits(1);
		}

		@Override
		public boolean shouldExecute() {
			return bush.getAttackTarget() == null && bush.rand.nextInt(100) > 60;
		}

		@Override
		public void startExecuting() {
			bush.setSleep(true);
			bush.setSleepTime(0);
			bush.setHealth(bush.getMaxHealth());
		}

		@Override
		public boolean shouldContinueExecuting() {
			
			if(bush.getAttackTarget() != null) {
				bush.setSleep(false);
				bush.setSleepTime(0);
				return false;
			}
			
			bush.setSleepTime(getSleepTime() + 1);
			if (bush.getSleepTime() > 1000) {
				bush.transformToBlock();
				return false;
			} else return true;
		}
	}

	public class EntityAISpawnAreaEffects extends EntityAIBase {
		
		private final EntityCurseBush bush;

		public EntityAISpawnAreaEffects(EntityCurseBush bush) {
			this.bush = bush;
			setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {
			return !bush.isSleep() && bush.getAttackTarget() != null && 
					bush.getAttackTarget().getDistance(bush) < 2.5F && rand.nextInt(100) > 70;
		}

		@Override
		public void startExecuting() {
			super.startExecuting();
		}
	}
}