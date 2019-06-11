package ru.mousecray.endmagic.entity;

import net.minecraft.block.Block;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.init.EMBlocks;

public class EntityCurseBush extends EntityMob {
	
	protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.<BlockPos>createKey(EntityCurseBush.class, DataSerializers.BLOCK_POS);
	
	private boolean isInvade;
	
	public EntityCurseBush(World world) {
		super(world);
		setJumping(false);
		setOrigin(new BlockPos(this));
		setSize(0.98F, 0.98F);
		isInvade = false;
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
		tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		tasks.addTask(5, new EntityAIOnBushTargetingEnemy(this));
		tasks.addTask(6, new EntityAITransformToBlock(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(15.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
			player.swingArm(hand);
			if (!this.world.isRemote) {
				setFire(3);
				itemstack.damageItem(3, player);
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
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("isInvade", isInvade);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		isInvade = compound.getBoolean("isInvade");
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		super.fall(distance, damageMultiplier);
		transformToBlock();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source == DamageSource.GENERIC) {
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, 5, 5, 5, Block.getStateId(EMBlocks.blockCurseBush.getDefaultState()));
			if(source.getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) source.getTrueSource();
				ItemStack stack = player.getHeldItem(getActiveHand());
				if(stack.getItem() instanceof ItemSword) {
					NBTTagList enchantments = stack.getEnchantmentTagList();
					for (int i = 0; i < enchantments.tagCount(); ++i) {
						NBTTagCompound tag = enchantments.getCompoundTagAt(i);
						Enchantment itemEnchant = Enchantment.getEnchantmentByID(tag.getShort("id"));
						if (itemEnchant == Enchantments.FIRE_ASPECT) {
							attackEntityFrom(DamageSource.ON_FIRE, 1.5F * (float)tag.getShort("lvl"));
							return false;
						}
					}
				}
			}
		}
		if (source.isFireDamage()) setFire(3);
		if(source.isFireDamage() || source.isMagicDamage()) return super.attackEntityFrom(source, amount);
		else return false;
	}

	public boolean transformToBlock() {
		navigator.tryMoveToXYZ(getPosition().getX()+0.5D, getPosition().getY(), getPosition().getZ()+0.5D, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
		setDead();
		world.setBlockState(getPosition(), EMBlocks.blockCurseBush.getDefaultState());
		return true;
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
		if (isInvade) getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
		else getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
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
	public boolean canBeCollidedWith() {
		return true;
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
		return SoundEvents.BLOCK_GRASS_BREAK;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_GRASS_FALL;
	}

	public class EntityAITransformToBlock extends EntityAIBase {

		private final EntityCurseBush bush;
		private int sleepTime;

		public EntityAITransformToBlock(EntityCurseBush bush) {
			this.bush = bush;
			setMutexBits(1);
		}		

		@Override
		public boolean shouldExecute() {
			return bush.getAttackTarget() == null && bush.getRNG().nextInt(10) > 5;
		}
		
		@Override
		public void startExecuting() {
			sleepTime = 20 + bush.getRNG().nextInt(100);
		}

		@Override
		public boolean shouldContinueExecuting() {
			if(bush.getAttackTarget() != null && sleepTime < 600) return true;
			else return bush.transformToBlock();
		}
		
		@Override
		public void updateTask() {
			++sleepTime;
		}
	}

	public class EntityAIOnBushTargetingEnemy extends EntityAIBase {
		
		private final EntityCurseBush bush;
		private int effect;

		public EntityAIOnBushTargetingEnemy(EntityCurseBush bush) {
			this.bush = bush;
			setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {
			return bush.getAttackTarget() != null && bush.getAttackTarget().getDistance(bush) < 5F;
		}
		
		@Override
		public void startExecuting() {
			effect = 100 + bush.getRNG().nextInt(100);
			bush.setInvade(true);
		}
		
		@Override
		public boolean shouldContinueExecuting() {
			if(bush.getAttackTarget() != null && bush.getAttackTarget().getDistance(bush) < 10F) return true;
			else {
				setInvade(false);
				return false;
			}
		}
		
		@Override
		public void updateTask() {
			--effect;
			if(effect == 0) {
				effect = 100 + bush.getRNG().nextInt(100);
				bush.world.spawnEntity(EMBlocks.blockCurseBush.getAreaEffect(world, getPosition()));
			}
		}
	}
}