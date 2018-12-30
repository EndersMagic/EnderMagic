package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.BlockWithTile;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.TileMasterBlockPortal;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MasterBlockPortal extends BlockWithTile<TileMasterBlockPortal> {
    public MasterBlockPortal() {
        super(Material.PORTAL);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote) {
            if (worldIn.isBlockPowered(pos)) {
                openPortal(pos, worldIn);
            }
        }
    }

    private void openPortal(BlockPos pos, World worldIn) {
        int limit = 10;//todo: extract to config
        int length = 0;
        BlockPos cur = pos.up();

        Location distination = tile(worldIn, pos).distination;

        List<BlockPos> portalPos = new ArrayList<>();

        while (worldIn.isAirBlock(cur) && length < limit) {
            portalPos.add(cur);

            cur = cur.up();
            length++;
        }

        if (worldIn.getBlockState(cur).getBlock() == EMBlocks.blockTopMark && !portalPos.isEmpty())
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

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMasterBlockPortal();
    }

}
