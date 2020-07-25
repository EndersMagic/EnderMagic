package ru.mousecray.endmagic.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.blocks.base.AutoMetaSubtypedBlock;

public class EMItemBlock extends ItemBlock {

    public EMItemBlock(Block block) {
        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return (block instanceof AutoMetaSubtypedBlock) ?
                ((AutoMetaSubtypedBlock) block).getUnlocalizedName(stack) :
                super.getUnlocalizedName(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return (block instanceof AutoMetaSubtypedBlock) ?
                block.getMetaFromState(((AutoMetaSubtypedBlock) block).getStateByItemStackDamage(damage)) :
                damage;
    }
}