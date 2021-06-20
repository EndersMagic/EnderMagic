package ru.mousecray.endmagic.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public abstract class BaseSlab extends BlockPurpurSlab implements ITechnicalBlock {
    private final Supplier<? extends BaseSlab> singleSlab;
    private final Supplier<? extends BaseSlab> doubleSlab;

    public BaseSlab(Material material, Supplier<? extends BaseSlab> singleSlab, Supplier<? extends BaseSlab> doubleSlab) {
        super();
        try {
            EnumHelper.setFailsafeFieldValue(Block.class.getDeclaredField("blockMaterial"), this, material);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(singleSlab.get());
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(singleSlab.get());
    }

    @Override
    public ItemBlock getCustomItemBlock(Block block) {
        if (isDouble())
            return null;
        else
            return new ItemSlab(singleSlab.get(), singleSlab.get(), doubleSlab.get());
    }

    @Nullable
    @Override
    public CreativeTabs getCustomCreativeTab() {
        if (isDouble())
            return null;
        else
            return EM.EM_CREATIVE;
    }
}
