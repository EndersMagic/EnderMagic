package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTopMark extends Block {
    public BlockTopMark() {
        super(Material.ROCK);
        setResistance(8.0F);
        setHardness(4.0F);
        setSoundType(SoundType.STONE);
    }
}
