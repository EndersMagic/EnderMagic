package ru.mousecray.endmagic.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ActivatorBase extends Default {
	
	public ActivatorBase(String name) {
		super(name, 1);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.hasTagCompound() && !stack.getTagCompound().hasNoTags()) {
			int[] pos1 = stack.getTagCompound().getIntArray("position");
			tooltip.add(I18n.format("tp.position") + ": " + pos1[0] + " " + pos1[1] + " " + pos1[2]);
		}
		else {
			tooltip.add(I18n.format("tp.position") + ": " + I18n.format("tp.nothing"));
		}
	}
}
