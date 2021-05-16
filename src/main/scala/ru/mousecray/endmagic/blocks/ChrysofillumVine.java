package ru.mousecray.endmagic.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import ru.mousecray.endmagic.api.blocks.EMBlockBush;
import ru.mousecray.endmagic.api.blocks.EnderPlantType;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.Random;

public class ChrysofillumVine extends EMBlockBush {

    public ChrysofillumVine() {
        super(Material.VINE, MapColor.BROWN);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return !(world.getBlockState(pos).getBlock() == EMBlocks.chrysVine || world.getBlockState(pos).getBlock() == EMBlocks.chrysFlower);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == EMBlocks.chrysVine || state.getBlock() == EMBlocks.naturalLog;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnderPlantType.em_hang;
    }
}