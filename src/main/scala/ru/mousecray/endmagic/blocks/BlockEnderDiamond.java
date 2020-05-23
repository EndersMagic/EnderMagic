package ru.mousecray.endmagic.blocks;

import net.minecraft.block.SoundType;

public class BlockEnderDiamond extends BlockNamed {
    public BlockEnderDiamond(String name) {
        super(name);
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
    }
}
