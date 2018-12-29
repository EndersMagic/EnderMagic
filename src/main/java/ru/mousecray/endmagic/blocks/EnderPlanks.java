package ru.mousecray.endmagic.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.blocks.item.IMetaBlockName;

public class EnderPlanks extends Default implements IMetaBlockName {
	
	public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumType.class);
	
	public EnderPlanks() {
		super(Material.WOOD, "ender_planks", 2F, 15F);
		setHarvestLevel("axe", 1);
        setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumType.DRAGON));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items) {
		for(int i = 0; i < EnumType.values().length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
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
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return ((EnumType)state.getValue(TYPE)).getMapColor();
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
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	public static enum EnumType implements IStringSerializable {
		DRAGON("dragon", 0, MapColor.PURPLE),
		NATURAL("natural", 1, MapColor.BLUE),
		IMMORTAL("immortal", 2, MapColor.EMERALD),
		VANISHING("vanishing", 3, MapColor.SILVER);
		
		private String name;
		private int ID;
        private MapColor mapColor;
	        
		private EnumType(String name, int ID, MapColor mapColor) {
			this.name = name;
			this.ID = ID;
            this.mapColor = mapColor;
		}
	        
		@Override
		public String getName() {
			return this.name;
		}
	      
		public int getID() {
			return this.ID;
		}
		
        public MapColor getMapColor() {
            return this.mapColor;
        }
	        
		@Override
		public String toString() {
			return getName();
		}
	}
}
