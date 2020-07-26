package ru.mousecray.endmagic.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.base.BaseTreeBlock;
import ru.mousecray.endmagic.tileentity.TilePhantomAvoidingBlockBase;

import java.util.List;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static ru.mousecray.endmagic.util.EnderBlockTypes.EnderTreeType.*;
import static ru.mousecray.endmagic.util.EnderBlockTypes.TREE_TYPE;

public class EMLog extends BaseTreeBlock {

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of(TREE_TYPE, LOG_AXIS);
    }

    public EMLog() {
        super(Material.WOOD);

        setHardness(2.5F);
        setResistance(4.0F);
        setSoundType(SoundType.WOOD);

        setHarvestLevel("axe", 2, getDefaultState().withProperty(TREE_TYPE, DRAGON));
        setHarvestLevel("axe", 2, getDefaultState().withProperty(TREE_TYPE, NATURAL));
        setHarvestLevel("axe", 3, getDefaultState().withProperty(TREE_TYPE, IMMORTAL));
        setHarvestLevel("axe", 1, getDefaultState().withProperty(TREE_TYPE, PHANTOM));

        setDefaultState(blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y)
                .withProperty(TREE_TYPE, DRAGON));

    }

    @Override
    protected String suffix() {
        return "log";
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
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

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(TREE_TYPE) == PHANTOM;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return state.getValue(TREE_TYPE) == PHANTOM ? new TilePhantomAvoidingBlockBase() : null;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return state.getValue(TREE_TYPE) == PHANTOM ?
                EnumBlockRenderType.ENTITYBLOCK_ANIMATED :
                EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return state.getValue(TREE_TYPE) != PHANTOM;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return state.getValue(TREE_TYPE) != PHANTOM;
    }
}
