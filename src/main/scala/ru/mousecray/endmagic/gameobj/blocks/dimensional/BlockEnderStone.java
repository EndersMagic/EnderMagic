package ru.mousecray.endmagic.gameobj.blocks.dimensional;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.gameobj.blocks.BlockTypeBase;
import ru.mousecray.endmagic.gameobj.blocks.VariativeBlock;

import javax.annotation.Nonnull;

public class BlockEnderStone<StoneType extends Enum<StoneType> & IStringSerializable & BlockTypeBase> extends VariativeBlock<StoneType> implements IEndSoil {
	public BlockEnderStone(Class<StoneType> type) {
		super(type, Material.ROCK, "stone");
		
		setHarvestLevel("pickaxe", 1);	
		setHardness(3.0F);
		setResistance(15.0F);
		setSoundType(SoundType.STONE);
	}
	
	@Nonnull
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