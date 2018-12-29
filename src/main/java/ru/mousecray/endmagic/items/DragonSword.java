package ru.mousecray.endmagic.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EndMagicData;

public class DragonSword extends ItemSword {
	
	public DragonSword(ToolMaterial material) {
		super(material);
		setRegistryName("dragon_sword");
		setUnlocalizedName("dragon_sword");
		setCreativeTab(EndMagicData.EM_CREATIVE);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!player.onGround) {
			if(!world.isRemote) {
				world.setBlockState(player.getPosition(), Blocks.WEB.getDefaultState());
				player.getHeldItem(hand).damageItem(10, player);
				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_CLOTH_FALL, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			}
	        player.addStat(StatList.getObjectUseStats(this));
	        player.setActiveHand(hand);
			player.getCooldownTracker().setCooldown(this, 1000);
	        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(I18n.format("tooltip.dragon_sword"));
	}
}