package ru.mousecray.endmagic.init;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.entity.EntityEnderArrow;
import ru.mousecray.endmagic.items.EnderArrow;

@EventBusSubscriber(modid=EM.ID)
public class EMEvents {

	//Not finished. 
	@SubscribeEvent
	public static void onPlayerLooseArrow(ArrowLooseEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		//get has arrow
        boolean flag = event.hasAmmo();
        //get has ender arrow
        ItemStack stack = findAmmo(player);
        
        if (flag && stack.getItem() == EMItems.enderArrow) {
        	//canceling vanilla event 
    		event.setCanceled(true);
    		
    		World world = event.getWorld();
    		
            float f = ItemBow.getArrowVelocity(event.getCharge());

            if ((double)f >= 0.1D) {
                boolean flag1 = player.capabilities.isCreativeMode || ((EnderArrow) stack.getItem()).isInfinite(stack, stack, player);

                if (!world.isRemote) {
                    EnderArrow arrow = (EnderArrow)(stack.getItem());
                    EntityEnderArrow entityarrow = arrow.createArrow(world, player);
                    entityarrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                    if (f == 1.0F) entityarrow.setIsCritical(true);

                    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

                    if (j > 0) entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);

                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

                    if (k > 0) entityarrow.setKnockbackStrength(k);

                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) entityarrow.setFire(100);

                    stack.damageItem(1, player);

                    if (flag1 || player.capabilities.isCreativeMode) entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;

                    world.spawnEntity(entityarrow);
                }

                world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (!flag1 && !player.capabilities.isCreativeMode) {
                    stack.shrink(1);

                    if (stack.isEmpty()) {
                        player.inventory.deleteStack(stack);
                    }
                }

                player.addStat(StatList.getObjectUseStats(stack.getItem()));
            }
        }
	}

	//TODO: Publishing standard ItemBow's method
	private static ItemStack findAmmo(EntityPlayer player) {
	    if (isArrow(player.getHeldItem(EnumHand.OFF_HAND))) return player.getHeldItem(EnumHand.OFF_HAND);
	    else if (isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) return player.getHeldItem(EnumHand.MAIN_HAND);
	    else for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
	    	ItemStack stack = player.inventory.getStackInSlot(i);
	    	if (isArrow(stack)) return stack;
	    }
	
	    return ItemStack.EMPTY;
	}

	private static boolean isEnderArrow(ItemStack stack) {
	    return stack.getItem() == EMItems.enderArrow;
	}
	
	private static boolean isArrow(ItemStack stack) {
	    return stack.getItem() instanceof ItemArrow;
	}
}
