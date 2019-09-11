package ru.mousecray.endmagic.blocks.trees;

import java.util.function.Function;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.blocks.EMSlab;

public class WoodenSlab extends EMSlab {

	public WoodenSlab(Class<?> type, boolean isDouble, Function<?, MapColor> mapColors) {
		super(type, Material.WOOD, isDouble, mapColors);
        IBlockState iblockstate = this.blockState.getBaseState();
        if (!isDouble) iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return null;
	}

	@Override
	public IProperty getVariantProperty() {
		return null;
	}

	@Override
	public Comparable getTypeForItem(ItemStack stack) {
		return null;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return null;
	}
}