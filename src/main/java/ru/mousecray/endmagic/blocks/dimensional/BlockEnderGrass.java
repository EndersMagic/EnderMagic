package ru.mousecray.endmagic.blocks.dimensional;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.IStringSerializable;
import ru.mousecray.endmagic.blocks.VariativeBlock;

public class BlockEnderGrass<GrassType extends Enum<GrassType> & IStringSerializable> extends VariativeBlock<GrassType> {

	public BlockEnderGrass(Class<GrassType> type) {
		super(type, Material.GRASS);
		
		setHarvestLevel("pickaxe", 1);	
		setHardness(3.0F);
		setResistance(10.0F);
		setTickRandomly(true);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
}