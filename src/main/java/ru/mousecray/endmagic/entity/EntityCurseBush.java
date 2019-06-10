package ru.mousecray.endmagic.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
	}
	
	public EntityCurseBush(World world, BlockPos pos) {
		this(world);
		setPosition(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D);
	}
	
	@Override
    protected void entityInit() {
		super.entityInit();
		dataManager.register(ORIGIN, BlockPos.ORIGIN);
    }

	@Override
    protected void initEntityAI() {
		tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
//        tasks.addTask(10, new AITransformToBlock(this));
        targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }
    
    @Override
    protected void applyEntityAttributes() {
    	super.applyEntityAttributes();       
    	getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.05D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
    }
    
    @Override
    public void onLivingUpdate() {
    	
    	super.onLivingUpdate();
    }
    
    @Override
    protected void updateAITasks() {
    	super.updateAITasks();
    	
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
    public boolean canBeCollidedWith() {
    	return false;
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
        setDead();
        super.fall(distance, damageMultiplier);
    }
    
    @Override
    public void onDeath(DamageSource cause) {
    	world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, 20, 20, 20, Block.getStateId(EMBlocks.blockCurseBush.getDefaultState()));
    	super.onDeath(cause);
    }
   
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {        
        if (source.isFireDamage()) {
        	damageEntity(source, 1.0F);
        	return true;
        }
        return super.attackEntityFrom(source, amount);
    }
    
    public void transformToBlock() {
		setDead();
		//TODO:AI
		world.setBlockState(getPosition(), EMBlocks.blockCurseBush.getDefaultState());
	}
    
    public boolean isInvade() {
		return isInvade;
	}
    
    public void setInvade(boolean isInvade) {
		this.isInvade = isInvade;
	}

    @Override 
    public boolean canBeHitWithPotion() {
    	return false;
    } 
    
    @Override 
    public boolean canBeAttackedWithItem() {
    	return false;
    }
    
    @SideOnly(Side.CLIENT) 
    public World getWorldObj() {
    	return this.world;
    }
    
    @SideOnly(Side.CLIENT) 
    public BlockPos getOrigin() {
    	return (BlockPos)this.dataManager.get(ORIGIN);
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

	public class AITransformToBlock extends EntityAIBase {
		private final EntityCurseBush entity;
		
		public AITransformToBlock(EntityCurseBush entity) {
			this.entity = entity;
		}
		
		@Override
		public boolean shouldExecute() {
			return false;
		}
	}
}