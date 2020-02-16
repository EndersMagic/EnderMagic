package ru.mousecray.endmagic.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EMBlockBush;
import ru.mousecray.endmagic.api.blocks.EndSoilType;

public class EnderOrchid extends EMBlockBush implements IShearable {

	public EnderOrchid() {
        super(Material.VINE);
        setHardness(0.0F);
        setResistance(0.0F);
        setSoundType(SoundType.PLANT);
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return EnderTallgrass.END_GRASS_AABB;
    }
    
    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return true;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override 
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) { 
    	return true; 
    }
    
    @Override
    public NonNullList onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return EMUtils.isSoil(state, true, false, EndSoilType.GRASS);
	}
}