package ru.mousecray.endmagic.blocks.purpur_quartz;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockPurpurQuartzPillar extends BlockRotatedPillar {
    public BlockPurpurQuartzPillar() {
        super(Material.ROCK, MapColor.MAGENTA);
        setHardness(1.5F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }
}
