package ru.mousecray.endmagic.api.blocks;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public abstract class EMBlockBush extends BlockBush {

	public EMBlockBush(Material material) {
		this(material, material.getMaterialMapColor());
	}

	public EMBlockBush(Material material, MapColor color) {
		super(material, color);
        setHardness(0.0F);
        setResistance(0.0F);
        setSoundType(SoundType.PLANT);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		if (EnderPlantType.isEMPlantType(plantable.getPlantType(world, pos.offset(direction)))) 
			return plantable instanceof EMBlockBush && ((EMBlockBush) plantable).canSustainBush(state);
		else return super.canSustainPlant(state, world, pos, direction, plantable);
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
		EnumPlantType plantType = getPlantType(world, pos);
        if (plantType == EnderPlantType.em_hang) {
			if (state.getBlock() == this) {
				IBlockState soil = world.getBlockState(pos.up());
				return this.canSustainPlant(soil, world, pos.up(), EnumFacing.DOWN, this);
			}
			return this.canSustainBush(world.getBlockState(pos.up()));
        }
        else if (EnderPlantType.isEMPlantType(plantType)) {
			if (state.getBlock() == this) {
				IBlockState soil = world.getBlockState(pos.down());
				return this.canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
			}
			return this.canSustainBush(world.getBlockState(pos.down()));
        }
        else return super.canBlockStay(world, pos, state);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		EnumPlantType plantType = getPlantType(world, pos);
        if (plantType == EnderPlantType.em_hang) {
			IBlockState soil = world.getBlockState(pos.up());
			return this.canSustainPlant(soil, world, pos.up(), EnumFacing.DOWN, this);
        }
        else if (EnderPlantType.isEMPlantType(plantType)) {
            IBlockState soil = world.getBlockState(pos.down());
            return world.getBlockState(pos).getBlock().isReplaceable(world, pos) && this.canSustainPlant(soil, world, pos.down(), net.minecraft.util.EnumFacing.UP, this);
        }
        else return super.canPlaceBlockAt(world, pos);
	}
	
	@Override 
	protected abstract boolean canSustainBush(IBlockState state);
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnderPlantType.end;
	}
}