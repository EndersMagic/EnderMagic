package ru.mousecray.endmagic.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.mousecray.endmagic.entity.EntityEnderArrow;

public class EnderArrow extends ItemArrow {
	
	public EntityEnderArrow createArrow(World world, EntityLivingBase shooter) {
		EntityEnderArrow arrow = new EntityEnderArrow(world, shooter);
		return arrow;
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
		int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
		return enchant <= 0 ? false : this.getClass() == EnderArrow.class;
	}
}
