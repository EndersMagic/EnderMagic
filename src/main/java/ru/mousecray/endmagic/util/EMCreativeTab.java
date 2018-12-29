package ru.mousecray.endmagic.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.blocks.ListBlock;

public class EMCreativeTab extends CreativeTabs {

	public EMCreativeTab() {
		super("em_cretive_tab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ListBlock.TP);
	}

}
