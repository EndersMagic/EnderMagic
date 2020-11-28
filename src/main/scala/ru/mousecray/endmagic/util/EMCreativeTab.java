package ru.mousecray.endmagic.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.init.EMItems;

import javax.annotation.Nonnull;

public class EMCreativeTab extends CreativeTabs
{
    public static boolean items = true, blocks = true, tools = true;

    public EMCreativeTab()
    {
        super("em_cretive_tab");
        setBackgroundImageName("em.png");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(EMBlocks.enderTallgrass);
    }

    @Override
    public boolean hasSearchBar()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> itemStacks)
    {
        if (items)  for (Item item : Item.REGISTRY) if (!(item instanceof ItemBlock) && !item.isDamageable()) item.getSubItems(this, itemStacks);
        if (blocks) for (Item item : Item.REGISTRY) if (item instanceof ItemBlock)                            item.getSubItems(this, itemStacks);
        if (tools)  for (Item item : Item.REGISTRY) if (item.isDamageable())                                  item.getSubItems(this, itemStacks);
        if(!itemStacks.contains(new ItemStack(EMItems.emBook)))
            EMItems.emBook.getSubItems(this, itemStacks);
    }
}
