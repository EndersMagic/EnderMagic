package ru.mousecray.endmagic.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.mousecray.endmagic.entity.EMEnderArrow;

public class EnderArrow extends ItemArrow {
	
	public EMEnderArrow createArrow(World world, EntityLivingBase shooter) {
		EMEnderArrow arrow = new EMEnderArrow(world, shooter);
		return arrow;
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
		int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
		return enchant <= 0 ? false : this.getClass() == EnderArrow.class;
	}
}
