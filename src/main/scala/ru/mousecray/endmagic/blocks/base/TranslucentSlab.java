package ru.mousecray.endmagic.blocks.base;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

public abstract class TranslucentSlab extends BaseSlab {

    public TranslucentSlab(Material material, Supplier<? extends TranslucentSlab> singleSlab, Supplier<? extends TranslucentSlab> doubleSlab) {
        super(material, singleSlab, doubleSlab);
        setSoundType(SoundType.STONE);
        setHardness(50);
        setResistance(2000);
        setLightOpacity(20);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }
}
