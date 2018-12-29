package ru.mousecray.endmagic.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import ru.mousecray.endmagic.blocks.item.IMetaBlockName;

public class EternalStone extends Default implements IMetaBlockName {
	
	public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumType.class);
	
	public EternalStone() {
		super(Material.ROCK, "EternalStone", 8.5F, 1000F);
		setHarvestLevel("pickaxe", 3);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumType.GREY));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items) {
		for(int i = 0; i < EnumType.values().length; i++) items.add(new ItemStack(this, 1, i));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {TYPE});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		EnumType type = (EnumType) state.getValue(TYPE);
		return type.getID();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, EnumType.values()[meta]);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		EnumType type = (EnumType) state.getValue(TYPE);
		return type.getID();
	}
	
	@Override
	public String getSpecialName(int damage) {
		return EnumType.values()[damage].getName();
	}
		
	@Override
	public String getSpecialName(ItemStack stack) {
		return EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public int getMetaCount() {
		return EnumType.values().length;
	}
	
	public static enum EnumType implements IStringSerializable {
		GREY("grey", 0),
		DARK("dark", 1),
		BLUE("blue", 2);

		private String name;
		private int ID;
	        
		private EnumType(String name, int ID) {
			this.name = name;
			this.ID = ID;
		}
	        
		@Override
		public String getName() {
			return this.name;
		}
	      
		public int getID() {
			return this.ID;
		}
	        
		@Override
		public String toString() {
			return getName();
		}		
	}
}