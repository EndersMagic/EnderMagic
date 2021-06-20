package ru.mousecray.endmagic.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TranslucentBlock extends Block {
    private final Class<?> extendedInterfaceMarker;

    public TranslucentBlock(Material blockMaterialIn, MapColor blockMapColorIn, Class<?> extendedInterfaceMarker) {
        super(blockMaterialIn, blockMapColorIn);
        this.extendedInterfaceMarker = extendedInterfaceMarker;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
