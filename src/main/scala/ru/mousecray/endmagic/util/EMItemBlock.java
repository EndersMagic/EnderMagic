package ru.mousecray.endmagic.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.gameobj.blocks.VariativeBlock;

public class EMItemBlock extends ItemBlock {
	
	public EMItemBlock(Block block) {
		super(block);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + ((VariativeBlock) this.block).getNameForStack(stack);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}