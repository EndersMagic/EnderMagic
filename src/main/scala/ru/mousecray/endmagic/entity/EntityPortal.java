package ru.mousecray.endmagic.entity;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.client.render.entity.RenderEntityPortal;
import ru.mousecray.endmagic.util.registry.EMEntity;

@EMEntity(renderClass = RenderEntityPortal.class)
public class EntityPortal extends Entity {

    private static final DataParameter<Integer> HEIGHT = EntityDataManager.createKey(EntityPortal.class, DataSerializers.VARINT);
    private static final DataParameter<BlockPos> MASTER_TILE_POS = EntityDataManager.createKey(EntityPortal.class, DataSerializers.BLOCK_POS);

    public EntityPortal(World worldIn) {
        super(worldIn);
        ignoreFrustumCheck = true;
        setSize(1f, 1f);
    }

    public EntityPortal(World worldIn, BlockPos masterPos, int height) {
        super(worldIn);
        ignoreFrustumCheck = true;
        setPosition(masterPos.getX() + 0.5, masterPos.getY() + 1, masterPos.getZ() + 0.5);
        setSize(1, height);
        setHeight(height);
        setMasterTilePos(masterPos);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public AxisAlignedBB getEntityBoundingBox() {
        return new AxisAlignedBB(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    @Override
    protected void entityInit() {
        getDataManager().register(HEIGHT, 0);
        getDataManager().register(MASTER_TILE_POS, BlockPos.ORIGIN);
    }

    public Integer getHeight() {
        return getDataManager().get(HEIGHT);
    }

    private void setHeight(int height) {
        getDataManager().set(HEIGHT, height);
    }

    private BlockPos getMasterTilePos() {
        return getDataManager().get(MASTER_TILE_POS);
    }

    private void setMasterTilePos(BlockPos masterPos) {
        getDataManager().set(MASTER_TILE_POS, masterPos);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (key == HEIGHT) {
            setSize(1, getHeight());
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        int height = compound.getInteger("HEIGHT");
        setSize(1, height);
        setHeight(height);
        setMasterTilePos(new BlockPos(compound.getInteger("MASTER_TILE_POS_X"), compound.getInteger("MASTER_TILE_POS_Y"), compound.getInteger("MASTER_TILE_POS_Z")));

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("HEIGHT", getHeight());
        compound.setInteger("MASTER_TILE_POS_X", getMasterTilePos().getX());
        compound.setInteger("MASTER_TILE_POS_Y", getMasterTilePos().getY());
        compound.setInteger("MASTER_TILE_POS_Z", getMasterTilePos().getZ());
    }

    public EnumPushReaction getPushReaction() {
        return EnumPushReaction.IGNORE;
    }
}
