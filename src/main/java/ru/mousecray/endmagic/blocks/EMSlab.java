package ru.mousecray.endmagic.blocks;

import java.util.Random;
import java.util.function.Function;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.util.EMUtils;
import ru.mousecray.endmagic.util.EnderBlockTypes.EMBlockHalf;

public class EMSlab<SlabType extends Enum<SlabType> & IStringSerializable> extends VariativeBlock<SlabType> {
	
    public static final PropertyEnum<EMBlockHalf> STATE = PropertyEnum.<EMBlockHalf>create("state", EMBlockHalf.class);
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	
    public EMSlab(Class<SlabType> type, Material material,Function<SlabType, MapColor> mapColors) {
		super(type, material, "slab", mapColors);
    }
    
    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(STATE) == EMBlockHalf.DOUBLE ? 255 : 0;
    }
    
    @Override
    public EMSlab setResistance(float resistance) {
        return (EMSlab) super.setResistance(resistance);
    }
    
    @Override
    public EMSlab setHardness(float hardness) {
        return (EMSlab) super.setHardness(hardness);
    }
    
    @Override
    public EMSlab setSoundType(SoundType sound) {
		return (EMSlab) super.setSoundType(sound);	
    }
	
	@Override
    protected boolean canSilkHarvest() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(STATE)) {
			case TOP: return AABB_TOP_HALF;
			case BOTTOM: return AABB_BOTTOM_HALF;
			case DOUBLE: default: return FULL_BLOCK_AABB;
		}
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return state.getValue(STATE) == EMBlockHalf.DOUBLE || state.getValue(STATE) == EMBlockHalf.TOP;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        if (state.getValue(STATE) == EMBlockHalf.DOUBLE) return BlockFaceShape.SOLID;
        else if (face == EnumFacing.UP && state.getValue(STATE) == EMBlockHalf.TOP) return BlockFaceShape.SOLID;
        else return face == EnumFacing.DOWN && state.getValue(STATE) == EMBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return state.getValue(STATE) == EMBlockHalf.DOUBLE;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
            return super.doesSideBlockRendering(state, world, pos, face);

        if (state.isOpaqueCube()) return true;

        EMBlockHalf side = state.getValue(STATE);
        return (side == EMBlockHalf.TOP && face == EnumFacing.UP) || (side == EMBlockHalf.BOTTOM && face == EnumFacing.DOWN);
    }
	
    //This IS NITHMAARE! Kill this code, please...
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(STATE, EMBlockHalf.BOTTOM);
        BlockPos blockpos = pos.offset(EMUtils.getNegativeFacing(facing));
        IBlockState state = world.getBlockState(blockpos);
        
        if (state.getBlock() == this && state.getValue(STATE) != EMBlockHalf.DOUBLE) {     	
            if (facing == EnumFacing.DOWN) {
            	if (state.getValue(STATE) == EMBlockHalf.BOTTOM) return iblockstate;
            	else {
                	//Dammet mousecray
                	world.setBlockState(blockpos, state.withProperty(STATE, EMBlockHalf.DOUBLE));
                	return Blocks.AIR.getDefaultState();
            	}
            }
            else if (facing == EnumFacing.UP) {
            	if(state.getValue(STATE) == EMBlockHalf.TOP) return iblockstate;
            	else {
            		world.setBlockState(blockpos, state.withProperty(STATE, EMBlockHalf.DOUBLE));
            		return Blocks.AIR.getDefaultState();
            	}
            }
            else return state;
        }
        else if (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D)) return iblockstate;
        else return iblockstate.withProperty(STATE, EMBlockHalf.TOP);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        int i = super.getMetaFromState(state);
        if (state.getValue(STATE) == EMBlockHalf.BOTTOM) return i;
        else if (state.getValue(STATE) == EMBlockHalf.TOP) return i |= 4;
        else return i |= 8;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	IBlockState state = super.getStateFromMeta(meta);
    	if (meta > 8) return state.withProperty(STATE, EMBlockHalf.DOUBLE);
    	else if (meta > 4) return state.withProperty(STATE, EMBlockHalf.TOP);
    	else return state.withProperty(STATE, EMBlockHalf.BOTTOM);
    }
    
    public int quantityDropped(IBlockState state, int fortune, Random random) {
    	return state.getValue(STATE) == EMBlockHalf.DOUBLE ? 2 : 1;
    }
	
    @Override
    public boolean isFullCube(IBlockState state) {
        return state.isOpaqueCube();
    }
    
    @SideOnly(Side.CLIENT)
    public static boolean isHalfSlab(IBlockState state) {
        return state.getValue(STATE) != EMBlockHalf.DOUBLE;
    }
    
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        if (blockState.isOpaqueCube()) return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side)) return false;
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
    
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STATE);
	}
	
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return byIndex.apply(stack.getMetadata() & 3);
    }
	
	public IProperty getVariantProperty() {
		return blockType;
	}
}