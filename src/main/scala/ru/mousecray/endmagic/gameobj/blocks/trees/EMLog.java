package ru.mousecray.endmagic.gameobj.blocks.trees;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.gameobj.blocks.BlockTypeBase;
import ru.mousecray.endmagic.gameobj.blocks.VariativeBlock;

import static net.minecraft.block.BlockLog.LOG_AXIS;

public class EMLog<TreeType extends Enum<TreeType> & IStringSerializable & BlockTypeBase> extends VariativeBlock<TreeType> {

    public EMLog(Class<TreeType> type) {
        super(type, Material.WOOD, "log");

        setHardness(2.5F);
        setResistance(4.0F);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 2, getDefaultState().withProperty(blockType, byIndex.apply(0)));
        setHarvestLevel("axe", 2, getDefaultState().withProperty(blockType, byIndex.apply(1)));
        setHarvestLevel("axe", 3, getDefaultState().withProperty(blockType, byIndex.apply(2)));
        setHarvestLevel("axe", 1, getDefaultState().withProperty(blockType, byIndex.apply(3)));

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

    @Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }
}
