package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.Block;
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
        super(Material.PORTAL);
    }

    public abstract boolean isValidDistination(Location loc, int lenght);

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

    private void openPortal(BlockPos pos, World worldIn) {
        int length = 0;
        BlockPos cur = pos.up();

        Location distination = tile(worldIn, pos).distination;

        List<BlockPos> portalPos = new ArrayList<>();

        while (worldIn.isAirBlock(cur) && length < limit) {
            portalPos.add(cur);

            cur = cur.up();
            length++;
        }

        if (isValidDistination(tile(worldIn, pos).distination, length) && worldIn.getBlockState(cur).getBlock() == EMBlocks.blockTopMark && !portalPos.isEmpty())
            portalPos.forEach(it -> setPortal(worldIn, it, distination));
        else
            doError();
    }

    private void doError() {
    }

    private void setPortal(World worldIn, BlockPos cur, Location distination) {
        worldIn.setBlockState(cur, EMBlocks.blockPortal.getDefaultState());
        EMBlocks.blockPortal.tile(worldIn, cur).distination = distination;
    }
}
