package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.portal.staticPortal.TileMasterStaticPortal;

import javax.annotation.Nullable;

public class BlockMasterStaticPortal extends BlockMasterPortal<TileMasterStaticPortal> {
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMasterStaticPortal();
    }

    @Override
    public boolean isValidDistination(Location loc, int ignored) {
        return loc.getBlockState().getBlock() == Blocks.BONE_BLOCK;
    }
}
