package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mousecray.endmagic.blocks.BlockWithNotifiableTile;
import ru.mousecray.endmagic.tileentity.portal.TileMasterBasePortal;

import java.util.function.Supplier;

public class BlockMasterBasePortal<A extends TileMasterBasePortal> extends BlockWithNotifiableTile<A> {
    public BlockMasterBasePortal(Supplier<A> factory) {
        super(Material.ROCK, factory);
        setResistance(8.0F);
        setHardness(4.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    protected static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return DEFAULT_AABB;
    }
}
