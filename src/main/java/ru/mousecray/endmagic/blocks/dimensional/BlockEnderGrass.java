package ru.mousecray.endmagic.blocks.dimensional;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.VariativeBlock;

public class BlockEnderGrass<GrassType extends Enum<GrassType> & IStringSerializable> extends VariativeBlock<GrassType> {

	public BlockEnderGrass(Class<GrassType> type) {
		super(type, Material.GRASS);
		
		setHarvestLevel("pickaxe", 1);	
		setHardness(3.0F);
		setResistance(10.0F);
		setTickRandomly(true);
		setSoundType(SoundType.GROUND);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		//TODO:Custom mechanics
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
		return state.getValue(blockType).ordinal() == 2 ? SoundType.SNOW : super.getSoundType(state, world, pos, entity);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
}