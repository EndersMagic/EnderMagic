package ru.mousecray.endmagic.blocks.trees;

import java.util.function.Function;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.IStringSerializable;
import ru.mousecray.endmagic.blocks.BlockTypeBase;
import ru.mousecray.endmagic.blocks.VariativeBlock;

public class EMPlanks<TreeType extends Enum<TreeType> & IStringSerializable & BlockTypeBase> extends VariativeBlock<TreeType> {

	public EMPlanks(Class<TreeType> type, Function<TreeType, MapColor> mapFunc) {
		super(type, Material.WOOD, "planks", mapFunc);
		setHardness(2.5F);
		setResistance(7.0F);
		setSoundType(SoundType.WOOD);	
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
}
