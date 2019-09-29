package ru.mousecray.endmagic.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import ru.mousecray.endmagic.api.blocks.IEndPlant;

public class EnderOrchid extends BlockBush implements IShearable, IEndPlant {

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
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) && isSoil(world, pos.down());
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return isSoil(world, pos.down());
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
        return Block.EnumOffsetType.XYZ;
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
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!world.isRemote && stack.getItem() == Items.SHEARS) player.addStat(StatList.getBlockStats(this));
        else super.harvestBlock(world, player, pos, state, te, stack);
    }
}