package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.IStringSerializable;
import ru.mousecray.endmagic.blocks.VariativeBlock;

public class EMPlanks<TreeType extends Enum<TreeType> & IStringSerializable> extends VariativeBlock<TreeType> {

	public EMPlanks(Class<TreeType> type) {
		super(type, Material.WOOD, "_planks");
		setHardness(2.5F);
		setResistance(7.0F);
		setSoundType(SoundType.WOOD);	
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
}
