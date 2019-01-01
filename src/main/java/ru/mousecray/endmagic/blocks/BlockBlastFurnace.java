package ru.mousecray.endmagic.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.TileBlastFurnace;

import javax.annotation.Nullable;

public class BlockBlastFurnace extends BlockWithTile<TileBlastFurnace> {
    public BlockBlastFurnace() {
        super(Material.ROCK);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlastFurnace();
    }
}
