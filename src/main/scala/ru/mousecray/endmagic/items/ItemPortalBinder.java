package ru.mousecray.endmagic.items;

import com.google.common.collect.ImmutableMap;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.TileWithLocation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ItemPortalBinder extends Item implements ItemTextured {
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(ChatFormatting.DARK_AQUA + readFromItem(stack).toString());
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        Block clickedBlock = world.getBlockState(pos).getBlock();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (player.isSneaking()) {
            storeToItem(new Location(pos, world), item);
            return EnumActionResult.SUCCESS;
        } else if (tileEntity instanceof TileWithLocation) {
            ((TileWithLocation) tileEntity).setDestination(readFromItem(item));

            return EnumActionResult.SUCCESS;
        } else
            return EnumActionResult.PASS;
    }

    private Location readFromItem(ItemStack item) {
        return Optional.ofNullable(item.getTagCompound())
                .map(i -> i.getCompoundTag("destination"))
                .map(Location::fromNbt).orElse(Location.spawn);
    }

    private void storeToItem(Location distination, ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());

        item.getTagCompound().setTag("destination", distination.toNbt());
    }

    @Override
    public Map<String, Integer> textures() {
        return ImmutableMap.of(EM.ID + ":items/item_portal_binder", 0xffffffff, EM.ID + ":items/item_portal_binder_glow", 0x3cffffff);
    }
}
