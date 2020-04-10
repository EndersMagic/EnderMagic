package ru.mousecray.endmagic.util.registry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;

public interface ITechnicalBlock extends IExtendedProperties {

    default ItemBlock getCustomItemBlock(Block block) {
        return null;
    }

    @Nullable
    @Override
    default CreativeTabs getCustomCreativeTab() {
        return null;
    }
}