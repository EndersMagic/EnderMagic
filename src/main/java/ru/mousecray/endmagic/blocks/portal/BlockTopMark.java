package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.portal.TileMark;

import javax.annotation.Nullable;

public class BlockTopMark extends BlockContainer {
    public BlockTopMark() {
        super(Material.ROCK);
    }
    
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMark();
    }

}
