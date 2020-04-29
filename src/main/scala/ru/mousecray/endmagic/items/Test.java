package ru.mousecray.endmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EM;

public class Test extends Item
{
	
	public Test() {
		setMaxStackSize(1);
		setUnlocalizedName("test");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if (playerIn.isSneaking())
		{
			if (playerIn.dimension == 0) playerIn.changeDimension(1);
			else if (playerIn.dimension == 1) playerIn.changeDimension(0);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
}