package ru.mousecray.endmagic.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.blocks.base.AutoMetaBlock;

public class EMItemBlock extends ItemBlock {
	
	public EMItemBlock(Block block) {
		super(block);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return (block instanceof AutoMetaBlock)?
				((AutoMetaBlock) block).getUnlocalizedName(stack):
				super.getUnlocalizedName(stack);
	}

	@Override
	public int getMetadata(int damage) {
		return (block instanceof AutoMetaBlock)?
				block.getMetaFromState(((AutoMetaBlock) block).getStateByItemStackDamage(damage)) :
				damage;
	}
}