package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import ru.mousecray.endmagic.blocks.BlockWithNotifiableTile;
import ru.mousecray.endmagic.tileentity.portal.TileTopMark;

public class BlockTopMark extends BlockWithNotifiableTile<TileTopMark> {
    public BlockTopMark() {
        super(Material.ROCK, TileTopMark::new);
        setResistance(8.0F);
        setHardness(4.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
