package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.TileMasterDarkPortal;

import javax.annotation.Nullable;

public class BlockMasterDarkPortal extends BlockMasterPortal<TileMasterDarkPortal> {
    @Override
    public boolean isValidDistination(Location loc, int sourceLength) {
        int length = 0;

        BlockPos pos = loc.toPos();

        BlockPos cur = pos.up();

        World worldIn = loc.getWorld();

        while (worldIn.isAirBlock(cur) && length < limit) {
            cur = cur.up();
            length++;
        }

        return worldIn.getBlockState(cur).getBlock() == EMBlocks.blockTopMark && length == sourceLength;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMasterDarkPortal();
    }
}
