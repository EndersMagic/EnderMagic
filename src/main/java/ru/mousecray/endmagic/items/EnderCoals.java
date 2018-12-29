package ru.mousecray.endmagic.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.blocks.EnderPlanks.EnumType;

public class EnderCoals extends DefaultMeta {

	public EnderCoals() {
		super("ender_coals");
	}

	@Override
	public void getSubItems(CreativeTabs item, NonNullList<ItemStack> items) {
		if(item == EndMagicData.EM_CREATIVE) for(int i = 0; i < EnumType.values().length; i++) items.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public ResourceLocation getRegistryName(int damage) {
		return new ResourceLocation(super.getRegistryName() + "." + EnumType.values()[damage].getName());
	}

	@Override
	public int getMetaCount() {
		return EnumType.values().length;
	}
}
