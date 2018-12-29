package ru.mousecray.endmagic.blocks.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TPItemBlock extends ItemBlock {
	
	public TPItemBlock(Block block) {
		super(block);
		if(!(block instanceof IMetaBlockName)) {
			throw new IllegalArgumentException(String
					.format("The given Block %s is not an instance of IMetaBlockName!", 
							block.getUnlocalizedName()));
		}
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + ((IMetaBlockName) this.block).getSpecialName(stack);
	}
	
	public ResourceLocation getRegistryName(int damage) {
		return new ResourceLocation(super.getRegistryName() + "." + ((IMetaBlockName) this.block).getSpecialName(damage));
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
