package ru.mousecray.endmagic.gameobj.blocks.trees;

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
import ru.mousecray.endmagic.gameobj.blocks.utils.AutoMetadataBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.List;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static ru.mousecray.endmagic.util.EnderBlockTypes.treeType;

public class EMLog extends AutoMetadataBlock {

    public EMLog() {
        super(Material.WOOD);

        setHardness(2.5F);
        setResistance(4.0F);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 2, getDefaultState().withProperty(treeType, EnderBlockTypes.EnderTreeType.DRAGON));
        setHarvestLevel("axe", 2, getDefaultState().withProperty(treeType, EnderBlockTypes.EnderTreeType.NATURAL));
        setHarvestLevel("axe", 3, getDefaultState().withProperty(treeType, EnderBlockTypes.EnderTreeType.IMMORTAL));
        setHarvestLevel("axe", 1, getDefaultState().withProperty(treeType, EnderBlockTypes.EnderTreeType.PHANTOM));

        setDefaultState(blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y)
                .withProperty(treeType, EnderBlockTypes.EnderTreeType.DRAGON));
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

    @Override
    public List<IProperty<?>> properties() {
        return ImmutableList.of(LOG_AXIS, treeType);
    }
}
