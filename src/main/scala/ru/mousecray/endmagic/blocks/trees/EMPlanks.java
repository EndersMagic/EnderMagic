package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import ru.mousecray.endmagic.blocks.base.BaseTreeBlock;

public class EMPlanks extends BaseTreeBlock {

    public EMPlanks() {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(7.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    protected String suffix() {
        return "planks";
    }
}
