package ru.mousecray.endmagic.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.entity.RenderEnderArrow;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.util.registry.EMEntity;

@EMEntity(renderClass = RenderEnderArrow.class)
public class EntityEnderArrow extends EntityArrow {

    private Block inTile;
    private int knockbackStrength;

    public EntityEnderArrow(World world) {
        super(world);
        setDamage(1D);
        pickupStatus = EntityArrow.PickupStatus.DISALLOWED;

        setSize(0.5F, 0.5F);
    }

    public EntityEnderArrow(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public EntityEnderArrow(World world, EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + (double) shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
        shootingEntity = shooter;

        if (shooter instanceof EntityPlayer) pickupStatus = PickupStatus.ALLOWED;
    }

    @Override
    protected void onHit(RayTraceResult raytraceResult) {
        Entity entity = raytraceResult.entityHit;

        if (entity != null) {
            float f = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
            int i = MathHelper.ceil((double) f * getDamage());

            if (getIsCritical()) i += rand.nextInt(i / 2 + 2);

            DamageSource damagesource;

            if (shootingEntity == null) damagesource = EM.causeArrowDamage(this, this);
            else damagesource = EM.causeArrowDamage(this, this);

            if (isBurning()) entity.setFire(5);

            if (entity.attackEntityFrom(damagesource, (float) i)) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

                    if (!world.isRemote) entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);

                    if (knockbackStrength > 0) {
                        float f1 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);

                        if (f1 > 0.0F) entitylivingbase.addVelocity(motionX * (double) knockbackStrength * 0.6000000238418579D / (double) f1, 0.1D,
                                motionZ * (double) knockbackStrength * 0.6000000238418579D / (double) f1);
                    }

                    if (shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) shootingEntity, entitylivingbase);
                    }

                    arrowHit(entitylivingbase);

                    if (shootingEntity != null && entitylivingbase != shootingEntity && entitylivingbase instanceof EntityPlayer &&
                            shootingEntity instanceof EntityPlayerMP)
                        ((EntityPlayerMP) shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                }

                playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

                setDead();
            } else {
                motionX *= -0.10000000149011612D;
                motionY *= -0.10000000149011612D;
                motionZ *= -0.10000000149011612D;
                rotationYaw += 180.0F;
                prevRotationYaw += 180.0F;
                if (!world.isRemote && motionX * motionX + motionY * motionY + motionZ * motionZ < 0.0010000000474974513D) {
                    if (pickupStatus == EntityArrow.PickupStatus.ALLOWED) entityDropItem(getArrowStack(), 0.1F);
                    setDead();
                }
            }
        } else {
            BlockPos blockpos = raytraceResult.getBlockPos();
            blockpos.getX();
            blockpos.getY();
            blockpos.getZ();
            IBlockState iblockstate = world.getBlockState(blockpos);
            inTile = iblockstate.getBlock();
            inTile.getMetaFromState(iblockstate);
            motionX = (double) ((float) (raytraceResult.hitVec.x - posX));
            motionY = (double) ((float) (raytraceResult.hitVec.y - posY));
            motionZ = (double) ((float) (raytraceResult.hitVec.z - posZ));
            float f2 = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
            posX -= motionX / (double) f2 * 0.05000000074505806D;
            posY -= motionY / (double) f2 * 0.05000000074505806D;
            posZ -= motionZ / (double) f2 * 0.05000000074505806D;
            playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
            inGround = true;
            arrowShake = 7;
            setIsCritical(false);

            if (iblockstate.getMaterial() != Material.AIR) inTile.onEntityCollidedWithBlock(world, blockpos, iblockstate, this);
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(EMItems.enderArrow);
    }
}