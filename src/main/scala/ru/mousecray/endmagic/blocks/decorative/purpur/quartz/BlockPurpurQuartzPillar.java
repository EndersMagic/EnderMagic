package ru.mousecray.endmagic.blocks.decorative.purpur.quartz;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.entity.EntityFixedItem;

import javax.annotation.Nullable;

public class BlockPurpurQuartzPillar extends BlockRotatedPillar {
    public BlockPurpurQuartzPillar() {
        super(Material.ROCK, MapColor.MAGENTA);
        setHardness(1.5F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote)
            if (entityIn instanceof EntityItem && !(entityIn instanceof EntityFixedItem) && noAlreadyExistsItem(worldIn, pos)) {
                EntityFixedItem fixedItem = new EntityFixedItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, (EntityItem) entityIn);
                entityIn.setDead();
                worldIn.spawnEntity(fixedItem);
            }
    }

    private boolean noAlreadyExistsItem(World worldIn, BlockPos pos) {
        return worldIn.getEntitiesWithinAABB(EntityFixedItem.class, new AxisAlignedBB(pos.up())).isEmpty();
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return FULL_BLOCK_AABB.setMaxY(0.99);
    }
}
