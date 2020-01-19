package ru.mousecray.endmagic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class TilePhantomAvoidingBlockBase extends TileEntity {
    int avoidTicks = 0;
    BlockPos masterPos;

    protected void teleportBlock(Vec3i offset) {
        IBlockState blockState = world.getBlockState(pos);
        world.setBlockToAir(pos);
        world.setBlockState(pos.add(offset), blockState);
        world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), offset.getX(), offset.getY(), offset.getZ());
    }

    public void notifyMasterAboutCutting() {
        TileEntity tileEntity = world.getTileEntity(masterPos);
        if (tileEntity instanceof TilePhantomAvoidingBlockMaster)
            ((TilePhantomAvoidingBlockMaster) tileEntity).notifyAboutCutting();
    }
}
