package ru.mousecray.endmagic.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class GeneratorBase extends Default {
	
	public static final PropertyInteger layers = PropertyInteger.create("layers", 1, 5);
	
	public GeneratorBase(String name) {
		super(Material.ROCK, name, "pickaxe", 1);
        setDefaultState(blockState.getBaseState().withProperty(layers, Integer.valueOf(1)));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(layers, Integer.valueOf((meta) + 1));
    }
	
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((Integer)state.getValue(layers)).intValue() - 1;
    }
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {layers});
    }
}
