package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.BlockWithTile;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.TileMasterPortal;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockMasterPortal<A extends TileMasterPortal> extends BlockWithTile<A> {
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

    public final int limit = 10;//todo: extract to config

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
