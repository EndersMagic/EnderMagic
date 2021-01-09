package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.BlockWithTile;
import ru.mousecray.endmagic.tileentity.portal.TileTopMark;

public class BlockTopMark extends BlockWithTile<TileTopMark> {
    public BlockTopMark() {
        super(Material.ROCK, TileTopMark::new);
        setResistance(8.0F);
        setHardness(4.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote)
            tile(worldIn,pos).neighborChanged();

    }
}
