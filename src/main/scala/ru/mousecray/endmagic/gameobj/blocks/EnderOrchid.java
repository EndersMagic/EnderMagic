package ru.mousecray.endmagic.gameobj.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import ru.mousecray.endmagic.api.blocks.EMBlockBush;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.utils.EMUtils;

import javax.annotation.Nonnull;
import java.util.Random;

public class EnderOrchid extends EMBlockBush implements IShearable {

    public EnderOrchid() {
        super(Material.VINE);
        setHardness(0.0F);
        setResistance(0.0F);
        setSoundType(SoundType.PLANT);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return EnderTallgrass.END_GRASS_AABB;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }

    @Override
    public boolean canSustainBush(IBlockState state) {
        //TODO: add custom end grass and remove STONE from this
        return EMUtils.isSoil(state, EndSoilType.STONE, EndSoilType.GRASS);
    }
}