package ru.mousecray.endmagic.blocks.item;

import net.minecraft.item.ItemStack;

public interface IMetaBlockName {
	String getSpecialName(int damage);
	String getSpecialName(ItemStack stack);
	int getMetaCount();
}
