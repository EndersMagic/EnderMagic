package ru.mousecray.endmagic.blocks.trees;

import java.util.Random;
import java.util.function.Function;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import ru.mousecray.endmagic.blocks.EMSlab;
import ru.mousecray.endmagic.init.EMBlocks;

public class WoodenSlab extends EMSlab {

	public WoodenSlab(Class<?> type, boolean isDouble, Function<?, MapColor> mapFunc) {
		super(type, Material.WOOD, isDouble, mapFunc);
        IBlockState iblockstate = this.blockState.getBaseState();
        if (!isDouble) iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(EMBlocks.enderWoodenHalfSlab);
    }

	@Override
	public IProperty getVariantProperty() {
		return blockType;
	}	

	@Override
	protected BlockStateContainer createBlockState() {
		return isDouble() ? new BlockStateContainer(this) : new BlockStateContainer(this, new IProperty[] {HALF});
	}
}