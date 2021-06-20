package ru.mousecray.endmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.rune.RuneEffectRegistry;
import ru.mousecray.endmagic.rune.RuneIndex;
import ru.mousecray.endmagic.rune.effects.HeatCatalystEffect;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

public class RuneDeployer extends Item {
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                RuneEffectRegistry.instance.getByName(NameAndTabUtils.getName(HeatCatalystEffect.class)).getLeft()
                        .forEach((coord, part) -> RuneIndex.addRunePart(world, pos, facing, coord, part));
            } else {
                ItemStack item = player.getHeldItem(hand);
                if (!item.hasTagCompound())
                    item.setTagCompound(new NBTTagCompound());
            }
        }
        return EnumActionResult.SUCCESS;
    }
}