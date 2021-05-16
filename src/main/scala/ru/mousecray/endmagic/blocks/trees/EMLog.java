package ru.mousecray.endmagic.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.base.BaseTreeBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.List;

import static net.minecraft.block.BlockLog.LOG_AXIS;

public abstract class EMLog extends BaseTreeBlock {

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of(LOG_AXIS);
    }

    public EMLog(EnderBlockTypes.EnderTreeType treeType) {
        super(Material.WOOD, treeType);

        setHardness(2.5F);
        setResistance(4.0F);
        setSoundType(SoundType.WOOD);

        setDefaultState(blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));

        for (IBlockState validState : getBlockState().getValidStates())
            setHarvestLevel("axe", 2, validState);
    }

    @Override
    protected String suffix() {
        return "log";
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    public boolean canSustainSpecifiedLeaves(IBlockAccess world, IBlockState logState, BlockPos logPos, IBlockState leavesState, BlockPos leavesPos) {
        if (leavesState.getBlock() instanceof BaseTreeBlock)
            return ((BaseTreeBlock) leavesState.getBlock()).treeType == treeType;
        else
            return false;
    }

    @Override
    public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return true;
    }
}
