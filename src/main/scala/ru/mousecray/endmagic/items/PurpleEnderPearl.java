package ru.mousecray.endmagic.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import ru.mousecray.endmagic.entity.EntityPurplePearl;
import ru.mousecray.endmagic.util.EntityImpact;

public class PurpleEnderPearl extends Item implements EntityImpact, ItemOneWhiteEMTextured {

    public PurpleEnderPearl() {
        setMaxStackSize(16);
    }

    @Override
    public void onImpact(EntityLivingBase result, EntityThrowable entity, EntityLivingBase thrower) {
        result.setPositionAndUpdate(thrower.posX, thrower.posY, thrower.posZ);
        result.fallDistance = 0.0F;
        result.attackEntityFrom(DamageSource.FALL, 5F);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.capabilities.isCreativeMode) itemstack.shrink(1);

        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        player.getCooldownTracker().setCooldown(this, 20);

        if (!world.isRemote) {
            EntityPurplePearl pearl = new EntityPurplePearl(world, player, itemstack);
            pearl.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(pearl);
        }

        player.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public String texture() {
        return "purple_ender_pearl";
    }
}