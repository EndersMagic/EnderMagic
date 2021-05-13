package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockPolishedObsidianEndstoneSlab extends BlockPurpurSlab implements ITechnicalBlock {

    public BlockPolishedObsidianEndstoneSlab() {
        super();
        setSoundType(SoundType.STONE);
        setHardness(50);
        setResistance(2000);
        setLightOpacity(20);
    }

    public static class BlockPolishedObsidianEndstoneSlabDouble extends BlockPolishedObsidianEndstoneSlab {
        public boolean isDouble() {
            return true;
        }
    }

    public static class BlockPolishedObsidianEndstoneSlabSingle extends BlockPolishedObsidianEndstoneSlab {
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
            return new ItemSlab(EMBlocks.polishedObsidianEndstoneSlabSingle, EMBlocks.polishedObsidianEndstoneSlabSingle, EMBlocks.polishedObsidianEndstoneSlabDouble);
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(EMBlocks.polishedObsidianEndstoneSlabSingle);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(EMBlocks.polishedObsidianEndstoneSlabSingle);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.PURPLE;
    }
}
