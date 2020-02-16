package ru.mousecray.endmagic.blocks.dimensional;

import java.util.function.Function;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.blocks.VariativeBlock;

public class BlockEnderStone<StoneType extends Enum<StoneType> & IStringSerializable> extends VariativeBlock<StoneType> implements IEndSoil {
	public BlockEnderStone(Class<StoneType> type, Function<StoneType, MapColor> mapFunc) {
		super(type, Material.ROCK, "stone", mapFunc);
		
		setHarvestLevel("pickaxe", 1);	
		setHardness(3.0F);
		setResistance(15.0F);
		setSoundType(SoundType.STONE);
	}
	
	@Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.SAND;
    }

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
	
	@Override
	public boolean canUseBonemeal() {
		return false;
	}
}