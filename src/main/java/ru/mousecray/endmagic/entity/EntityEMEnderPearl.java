package ru.mousecray.endmagic.entity;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.items.EMEnderPearl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityEMEnderPearl extends EntityThrowable {

    private static final DataParameter<ItemStack> itemStack = EntityDataManager
            .createKey(EntityEMEnderPearl.class, DataSerializers.ITEM_STACK);
    private EntityLivingBase perlThrower;

    public EntityEMEnderPearl(World world) {
        super(world);
    }

    public EntityEMEnderPearl(World world, EntityLivingBase thrower, ItemStack itemStack) {
        super(world, thrower);
        perlThrower = thrower;
        setItemStack(itemStack);
    }

    public EntityEMEnderPearl(World world, double x, double y, double z, ItemStack itemStack) {
        super(world, x, y, z);
        setItemStack(itemStack);
    }

    @Override
    protected void entityInit() {
        getDataManager().register(itemStack, ItemStack.EMPTY);
    }

    @Override
    public void onUpdate() {
        EntityLivingBase entitylivingbase = getThrower();

        if (entitylivingbase instanceof EntityPlayer && !entitylivingbase.isEntityAlive()) setDead();
        else super.onUpdate();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit == perlThrower) return;

        if (!world.isRemote) {
            if (result.entityHit instanceof EntityLivingBase) {
                EntityLivingBase resultEntity = (EntityLivingBase) result.entityHit;
                ItemStack stack = getItemStack();
                EMEnderPearl item = (EMEnderPearl) stack.getItem();
                EntityLivingBase factionalThrower = getThrower();
                if (factionalThrower instanceof EntityPlayerMP) {
                    EntityPlayerMP entityplayermp = (EntityPlayerMP) factionalThrower;

                    if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == world
                            && !entityplayermp.isPlayerSleeping()) {
                        EnderTeleportEvent event = new EnderTeleportEvent(resultEntity, factionalThrower.posX, factionalThrower.posY,
                                factionalThrower.posZ, 0F);
                        if (!MinecraftForge.EVENT_BUS.post(event)) {
                            spawnEndermite(result.entityHit, true);
                            item.onImpact((EntityLivingBase) result.entityHit, factionalThrower, this);
                        }
                    }
                } else {
                    spawnEndermite(result.entityHit, false);
                    item.onImpact((EntityLivingBase) result.entityHit, factionalThrower, this);
                }
            }

            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                for (int i = -2; i < 2; ++i)
                    for (int j = -2; j < 2; ++j) {
                        BlockPos blockpos = result.getBlockPos().add(i, 0, j);
                        TileEntity tileentity = world.getTileEntity(blockpos);
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
                BlockPos blockpos = result.getBlockPos();
                for (int i = 0; i < 32; ++i)
                    world.spawnParticle(EnumParticleTypes.PORTAL, blockpos.getX(),
                            blockpos.getY() + rand.nextDouble() * 2.0D, blockpos.getZ(), rand.nextGaussian(), 0.0D,
                            rand.nextGaussian());
            }
            world.setEntityState(this, (byte) 3);
            setDead();
        }
    }

    protected void spawnEndermite(Entity entityHit, boolean spawnedByPlayer) {
        if (rand.nextFloat() < 0.05F && world.getGameRules().getBoolean("doMobSpawning")) {
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

        if (!itemstack.isEmpty()) compound.setTag("ItemStack", itemstack.writeToNBT(new NBTTagCompound()));
    }

    public ItemStack getItemStack() {
        ItemStack itemstack = getDataManager().get(itemStack);
        if (!(itemstack.getItem() instanceof EMEnderPearl)) return new ItemStack(EMItems.purpleEnderPearl);
        else return itemstack;
    }

    public void setItemStack(ItemStack stack) {
        getDataManager().set(itemStack, stack.copy());
        getDataManager().setDirty(itemStack);
    }

    @Override
    @Nullable
    public Entity changeDimension(int dimension, @Nonnull ITeleporter teleporter) {
        if (thrower != null && thrower.dimension != dimension) thrower = null;
        return super.changeDimension(dimension, teleporter);
    }
}