package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.VariativeBlock;

import static net.minecraft.block.BlockLog.LOG_AXIS;

public class EMLog<TreeType extends Enum<TreeType> & IStringSerializable> extends VariativeBlock<TreeType> {

    public EMLog(Class<TreeType> type) {
        super(type, Material.WOOD, "_log");

        setHardness(2.0F);
        setSoundType(SoundType.WOOD);

        setDefaultState(blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y)
                .withProperty(blockType, byIndex.apply(0)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState r = super.getStateFromMeta(meta);
        int axis = meta >> 2;
        return r.withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[axis]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int r = super.getMetaFromState(state);
        return (state.getValue(LOG_AXIS).ordinal() << 2) + r;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return true;
    }
}
