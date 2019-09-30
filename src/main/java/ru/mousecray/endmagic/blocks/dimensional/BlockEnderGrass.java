package ru.mousecray.endmagic.blocks.dimensional;

import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.blocks.VariativeBlock;
import ru.mousecray.endmagic.init.EMBlocks;

public class BlockEnderGrass<GrassType extends Enum<GrassType> & IStringSerializable> extends VariativeBlock<GrassType> implements IEndSoil {
	
	private final Function<GrassType, SoundType> soundFunc;
	
	public BlockEnderGrass(Class<GrassType> type, Function<GrassType, MapColor> mapColor, Function<GrassType, SoundType> soundFunc) {
		super(type, Material.ROCK, "grass", mapColor);
		
		this.soundFunc = soundFunc;
		setHarvestLevel("pickaxe", 1);	
		setHardness(3.0F);
		setResistance(10.0F);
		setTickRandomly(true);
	}
	
	@Override
	public EndSoilType getSoilType() {
		return EndSoilType.GRASS;
	}
	
	@Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		return soundFunc.apply(state.getValue(blockType));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(EMBlocks.blockEnderStone);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		//TODO:Custom mechanics
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
}