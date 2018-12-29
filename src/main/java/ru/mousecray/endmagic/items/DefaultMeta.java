package ru.mousecray.endmagic.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public abstract class DefaultMeta extends Default {

	public DefaultMeta(String name) {
		super(name);
		setHasSubtypes(true);
	}

	@Override
	public abstract void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items);
	@Override
	public abstract String getUnlocalizedName(ItemStack stack);
	public abstract int getMetaCount();
	public abstract ResourceLocation getRegistryName(int damage);
}