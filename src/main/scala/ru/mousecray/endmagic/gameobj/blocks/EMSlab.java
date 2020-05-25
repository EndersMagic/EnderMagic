package ru.mousecray.endmagic.gameobj.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes.EMBlockHalf;

import javax.annotation.Nonnull;

import static ru.mousecray.endmagic.util.EnderBlockTypes.BLOCK_HALF;

public class EMSlab extends MetadataBlock {

    public EMSlab(Material material) {
        super(material);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockStateContainer() {
        return BlockStateGenerator.create(this).addFeature(BLOCK_HALF, false).buildContainer();
    }

    //Overload method for public access
    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, @Nonnull IBlockState state, BlockPos pos, EnumFacing face) {
        if (state.getValue(BLOCK_HALF) == EMBlockHalf.DOUBLE) return BlockFaceShape.SOLID;
        else if (face == EnumFacing.UP && state.getValue(BLOCK_HALF) == EMBlockHalf.TOP) return BlockFaceShape.SOLID;
        else return face == EnumFacing.DOWN && state.getValue(BLOCK_HALF) == EMBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
            return super.doesSideBlockRendering(state, world, pos, face);

        if (state.isOpaqueCube()) return true;

        EMBlockHalf side = state.getValue(BLOCK_HALF);
        return (side == EMBlockHalf.TOP && face == EnumFacing.UP) || (side == EMBlockHalf.BOTTOM && face == EnumFacing.DOWN);
    }

//    //This IS NITHMAARE! Kill this code, please...
//    @Override
//    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
//        IBlockState iblockstate = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(STATE,
//                EMBlockHalf.BOTTOM);
//        BlockPos blockpos = pos.offset(EMUtils.getNegativeFacing(facing));
//        IBlockState state = world.getBlockState(blockpos);
//
//        if (state.getBlock() == this && state.getValue(STATE) != EMBlockHalf.DOUBLE)
//            if (facing == EnumFacing.DOWN) if (state.getValue(STATE) == EMBlockHalf.BOTTOM) return iblockstate;
//            else {
//                //Dammet mousecray
//                world.setBlockState(blockpos, state.withProperty(STATE, EMBlockHalf.DOUBLE));
//                return Blocks.AIR.getDefaultState();
//            }
//            else if (facing == EnumFacing.UP) if (state.getValue(STATE) == EMBlockHalf.TOP) return iblockstate;
//            else {
//                world.setBlockState(blockpos, state.withProperty(STATE, EMBlockHalf.DOUBLE));
//                return Blocks.AIR.getDefaultState();
//            }
//            else return state;
//        else if (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D)) return iblockstate;
//        else return iblockstate.withProperty(STATE, EMBlockHalf.TOP);
//    }

    @SideOnly(Side.CLIENT)
    public static boolean isHalfSlab(@Nonnull IBlockState state) {
        return state.getValue(BLOCK_HALF) != EMBlockHalf.DOUBLE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, EnumFacing side) {
        if (blockState.isOpaqueCube()) return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side)) return false;
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}