package ru.mousecray.endmagic.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.init.EMBlocks;

import javax.annotation.Nonnull;

public class EMCreativeTab extends CreativeTabs {
    public static int Sort = 0;

    public EMCreativeTab() {
        super("em_cretive_tab");
        setBackgroundImageName("item_search.png");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(EMBlocks.enderTallgrass);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> itemStacks) {
        switch (Sort) {
            case 0:
                for (Item item : Item.REGISTRY)
                    item.getSubItems(this, itemStacks);

                break;
            case 1:
                for (Item item : Item.REGISTRY)
                    if (!(item instanceof ItemBlock) && !item.isDamageable()) item.getSubItems(this, itemStacks);

                break;
            case 2:
                for (Item item : Item.REGISTRY)
                    if (item instanceof ItemBlock) item.getSubItems(this, itemStacks);

                break;
            case 3:
                for (Item item : Item.REGISTRY)
                    if (item.isDamageable()) item.getSubItems(this, itemStacks);

        }
    }
}
