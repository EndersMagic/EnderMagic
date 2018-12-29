package ru.mousecray.endmagic.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EndMagicData;

public class DragonPickaxe extends ItemPickaxe {
	
    public DragonPickaxe(ToolMaterial material) {
        super(material);
        setRegistryName("dragon_pickaxe");
        setUnlocalizedName("dragon_pickaxe");
		setCreativeTab(EndMagicData.EM_CREATIVE);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	ItemStack itemstack = player.getHeldItem(hand);
    	
    	player.getCooldownTracker().setCooldown(this, 1500);
    	if (!world.isRemote) {
//    		int heading = MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//    		
//    		EnumFacing angle = EnumFacing.getFront(heading);
    		
    		player.getHeldItem(hand).damageItem(3, player);
    		world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    		
    		for(int x = -2; x <= 2; x++) {
    			for(int z = -2; z <= 2; z++) {
    				world.destroyBlock(pos.add(x, 0, z), true);
    			}
    		}
    		for(int x = -1; x <= 1; x++) {
    			for(int z = -1; z <= 1; z++) {
    				world.destroyBlock(pos.add(x, -1, z), true);
    			}
    		}
			world.destroyBlock(pos.add(0, -2, 0), true);
    		
    	}
	    	
    	player.addStat(StatList.getObjectUseStats(this));
        return EnumActionResult.SUCCESS;
    }
    
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(I18n.format("tooltip.dragon_pickaxe"));
	}
}