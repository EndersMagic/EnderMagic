package ru.mousecray.endmagic.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMBlocks;

public class EntityCurseBush extends EntityMob {
	
	public EntityCurseBush(World world) {
		super(world);
		isJumping = false;
	}

    protected void initEntityAI() {
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(10, new AITransformToBlock(this));
        this.targetTasks.addTask(1, new AIFindPlayer(this));
    }
    
    @Override
    protected void applyEntityAttributes() {
    	getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.MAX_HEALTH);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.05D);
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {	 	
    	super.writeEntityToNBT(compound);
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
    	super.readEntityFromNBT(compound);
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
    public boolean canBeHitWithPotion() {
    	return false;
    }
    
    @Override
    public boolean canBeAttackedWithItem() {
    	return false;
    }
    
//    @Override
//    public boolean hitByEntity(Entity entity) {
//        return true;
//    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {        
        if (this.isEntityInvulnerable(source)) return false;
        else if (source.isFireDamage()) return true;
        return super.attackEntityFrom(source, amount);
    }
    
    public void setToBlock() {
		setDead();
		world.setBlockState(getPosition(), EMBlocks.blockCurseBush.getDefaultState());
	}
    
    @Override protected SoundEvent getAmbientSound() {return SoundEvents.BLOCK_GRASS_HIT;}
    
    @Override protected SoundEvent getHurtSound(DamageSource damageSource) {return SoundEvents.BLOCK_GRASS_STEP;}

    @Override protected SoundEvent getDeathSound() {return SoundEvents.BLOCK_GRASS_FALL;}
    
	public class AIFindPlayer extends EntityAIFindEntityNearestPlayer {

		public AIFindPlayer(EntityLiving entityLiving) {
			super(entityLiving);
		}

		@Override
		public boolean shouldExecute() {
			return false;
		}

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