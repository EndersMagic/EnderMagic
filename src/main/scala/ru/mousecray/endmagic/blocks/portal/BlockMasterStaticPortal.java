package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.staticPortal.TileMasterStaticPortal;

import javax.annotation.Nullable;
import java.util.List;

public class BlockMasterStaticPortal extends BlockMasterPortal<TileMasterStaticPortal> {
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMasterStaticPortal();
    }

    @Override
    protected boolean checkAndPrepareDestinition(Location distination, List<BlockPos> portalPos) {
        return distination.getBlockState().getBlock() == Blocks.BONE_BLOCK;
    }
}
