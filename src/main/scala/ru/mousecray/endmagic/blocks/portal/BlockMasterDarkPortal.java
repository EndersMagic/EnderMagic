package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.TileMasterDarkPortal;

import javax.annotation.Nullable;
import java.util.List;

public class BlockMasterDarkPortal extends BlockMasterPortal<TileMasterDarkPortal> {

    private boolean openPairPortal(Location distination, int sourceLength) {
        BlockPos pos = distination.toPos();
        World worldIn = distination.getWorld();

        if (worldIn.getBlockState(pos).getBlock() == EMBlocks.blockMasterDarkPortal) {
            TileMasterDarkPortal distinationMasterTile = tile(worldIn, pos);

            List<BlockPos> portalPos = getEmptyPoses(pos.up(), worldIn);

            if (portalPos.size() == sourceLength) {
                distinationMasterTile.openPortal(portalPos);
                portalPos.forEach(it -> setPortal(worldIn, it, pos));
                return true;
            } else
                return false;
        } else
            return false;
    }

    @Override
    protected boolean checkAndPrepareDestinition(Location distination, List<BlockPos> portalPos) {
        return openPairPortal(distination, portalPos.size());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMasterDarkPortal();
    }
}
