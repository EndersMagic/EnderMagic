package ru.mousecray.endmagic.blocks.portal;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.Configuration;
import ru.mousecray.endmagic.blocks.BlockWithTile;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.TileMasterPortal;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockMasterPortal<A extends TileMasterPortal> extends BlockWithTile<A> {

    protected static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    public BlockMasterPortal() {
        super(Material.ROCK);
        setResistance(8.0F);
        setHardness(4.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote) {
            if (worldIn.isBlockPowered(pos)) {
                openPortal(pos, worldIn);
            }
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return DEFAULT_AABB;
    }

    public final int limit = Configuration.portalSizeLimit;

    protected void openPortal(BlockPos pos, World worldIn) {
        A currentMasterTile = tile(worldIn, pos);
        Location distination = currentMasterTile.distination;

        List<BlockPos> portalPos = getEmptyPoses(pos.up(), worldIn);

        if (!portalPos.isEmpty() && checkAndPrepareDestinition(distination, portalPos)) {
            currentMasterTile.openPortal(portalPos);
            portalPos.forEach(it -> setPortal(worldIn, it, pos));
        } else
            doError();
    }

    protected abstract boolean checkAndPrepareDestinition(Location distination, List<BlockPos> portalPos);

    List<BlockPos> getEmptyPoses(BlockPos from, World world) {
        int length = 0;
        BlockPos cur = from;

        List<BlockPos> portalPos = new ArrayList<>();

        while (world.isAirBlock(cur) && length < limit) {
            portalPos.add(cur);

            cur = cur.up();
            length++;
        }

        if (world.getBlockState(cur).getBlock() == EMBlocks.blockTopMark)
            return portalPos;
        else
            return ImmutableList.of();
    }

    protected void doError() {
    }

    void setPortal(World worldIn, BlockPos portalPos, BlockPos masterPos) {
        worldIn.setBlockState(portalPos, EMBlocks.blockPortal.getDefaultState());
        EMBlocks.blockPortal.tile(worldIn, portalPos).masterTilePos = masterPos;
    }
}
