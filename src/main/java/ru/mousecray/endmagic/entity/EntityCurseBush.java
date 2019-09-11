package ru.mousecray.endmagic.entity;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMBlocks;

public class EntityCurseBush extends EntityMob {
	
	protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.<BlockPos>createKey(EntityCurseBush.class, DataSerializers.BLOCK_POS);
	private boolean isBlock;
	private BlockPos posToBlock;
	
	public EntityCurseBush(World world) {
		super(world);
		setJumping(false);
		setOrigin(new BlockPos(this));
		setSize(0.98F, 0.98F);
	}
	
	public EntityCurseBush(World world, BlockPos pos) {
		this(world);
		setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(isBlock) {
			setDead();
			if(!world.isRemote) world.setBlockState(posToBlock, EMBlocks.blockCurseBush.getDefaultState());
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(ORIGIN, BlockPos.ORIGIN);
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
		tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		tasks.addTask(5, new EntityAIOnBushSummonEffect(this));
		tasks.addTask(6, new EntityAITransformToBlock(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(15.0);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
			player.swingArm(hand);
			if (!this.world.isRemote) {
				setFire(3);
				dealFireDamageM(1.0F);
				itemstack.damageItem(10, player);
				return true;
			}
		}
		else if (itemstack.getItem() == Items.SHEARS) {
			NBTTagList enchantments = itemstack.getEnchantmentTagList();
			for (int i = 0; i < enchantments.tagCount(); ++i) {
				NBTTagCompound tag = enchantments.getCompoundTagAt(i);
				Enchantment itemEnchant = Enchantment.getEnchantmentByID(tag.getShort("id"));
				if (itemEnchant == Enchantments.SILK_TOUCH)  {
					attackEntityFrom(DamageSource.MAGIC, getMaxHealth());
					itemstack.damageItem(10, player);
					return true;
				}
			}
		}

		return super.processInteract(player, hand);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		if (distance > 2F) transformToBlock();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.getTrueSource() instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) source.getTrueSource();
			ItemStack stack = living.getHeldItem(living.getActiveHand());
			if(stack.getItem() instanceof ItemSword) {
				NBTTagList enchantments = stack.getEnchantmentTagList();
				for (int i = 0; i < enchantments.tagCount(); ++i) {
					NBTTagCompound tag = enchantments.getCompoundTagAt(i);
					Enchantment itemEnchant = Enchantment.getEnchantmentByID(tag.getShort("id"));
					if (itemEnchant == Enchantments.FIRE_ASPECT) {
						setFire(3);
						dealFireDamageM(tag.getShort("lvl"));
						return true;
					}
				}
			}
		}
		if (source.isMagicDamage()) {
			spawnParticles(world, getPosition(), 7);
			return super.attackEntityFrom(source, amount);
		}
		else if(source.isFireDamage()) {
			spawnParticles(world, getPosition(), 7);
			setFire(3);
			return super.attackEntityFrom(source, amount);
		}
		else return false;
	}
	
    protected void dealFireDamageM(float amount) {
        if (!isImmuneToFire) attackEntityFrom(DamageSource.IN_FIRE, amount);
    }
	
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
		if(source.isFireDamage()) InventoryHelper.spawnItemStack(world, posX, posY, posZ, new ItemStack(Items.COAL));
	};

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
	public boolean canBePushed() {
		return false;
	}

	@Override
	public void collideWithNearbyEntities() {}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.BLOCK_GRASS_HIT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.BLOCK_GRASS_FALL;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_GRASS_BREAK;
	}

	public void transformToBlock() {
		posToBlock = getPosition();
		isBlock = true;
	}
	
	public void spawnParticles(World world, BlockPos pos, int count) {
		for (int j = 0; j < count; j++) {
			double rx = EM.rand.nextGaussian();
			rx = MathHelper.clamp(rx, -1.0D, 1.0D);
			double rz = EM.rand.nextGaussian();
			rz = MathHelper.clamp(rz, -1.0D, 1.0D);
			double xCoord = pos.getX() + 0.5D + rx;
			double zCoord = pos.getZ() + 0.5D + rz;
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, xCoord, pos.getY()+0.25D, zCoord, 0.0D, 0.0D, 0.0D, Block.getStateId(EMBlocks.blockCurseBush.getDefaultState()));
		}
	}

	public class EntityAITransformToBlock extends EntityAIBase {

		private final EntityCurseBush bush;
		private int sleepTime;

		public EntityAITransformToBlock(EntityCurseBush bush) {
			this.bush = bush;
			setMutexBits(5);
		}		

		@Override
		public boolean shouldExecute() {
			return bush.getAttackTarget() == null;
		}
		
		@Override
		public void startExecuting() {
			sleepTime = 30 + bush.getRNG().nextInt(10);
		}

		@Override
		public boolean shouldContinueExecuting() {
			if(bush.getAttackTarget() != null) return false;		
			if(sleepTime < 600) return true;
			else return false;
		}
		
		@Override
		public void updateTask() {
			++sleepTime;
			if(sleepTime >= 600) bush.transformToBlock();
		}
	}

	public class EntityAIOnBushSummonEffect extends EntityAIBase {
		
		private final EntityCurseBush bush;
		private int effect;
		private int delayToEnd;

		public EntityAIOnBushSummonEffect(EntityCurseBush bush) {
			this.bush = bush;
			setMutexBits(8);
		}

		@Override
		public boolean shouldExecute() {
			return bush.getAttackTarget() != null && bush.getRNG().nextInt(5) > 3;
		}
		
		@Override
		public void startExecuting() {
			effect = 200 + bush.getRNG().nextInt(200);
			delayToEnd = bush.getRNG().nextInt(400) + 400;
		}
		
		@Override
		public boolean shouldContinueExecuting() {
			if(bush.getAttackTarget() == null && delayToEnd <= 0) return false;
			else return true;
		}
		
		@Override
		public void updateTask() {
			--effect;
			--delayToEnd;
			if(effect <= 0) {
				if (!world.isRemote) bush.world.spawnEntity(EMBlocks.blockCurseBush.getAreaEffect(world, bush.getPosition()));
				effect = 200 + bush.getRNG().nextInt(200);
			}
		}
	}
}