package ru.mousecray.endmagic.blocks.purpur_quartz;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockPurpurQuartzSlab extends BlockPurpurSlab implements ITechnicalBlock {

    public BlockPurpurQuartzSlab() {
        super();
        setSoundType(SoundType.STONE);
        setHardness(1.5F);
        setResistance(10.0F);
    }

    public static class BlockPurpurQuartzSlabDouble extends BlockPurpurQuartzSlab {
        public boolean isDouble() {
            return true;
        }
    }

    public static class BlockPurpurQuartzSlabSingle extends BlockPurpurQuartzSlab {
        public boolean isDouble() {
            return false;
        }

        @Nullable
        @Override
        public CreativeTabs getCustomCreativeTab() {
            return EM.EM_CREATIVE;
        }

        @Override
        public ItemBlock getCustomItemBlock(Block block) {
            return new ItemSlab(EMBlocks.purpurSlabSingle, EMBlocks.purpurSlabSingle, EMBlocks.purpurSlabDouble);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(EMBlocks.purpurSlabSingle);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(EMBlocks.purpurSlabSingle);
    }
}
