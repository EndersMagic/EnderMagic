package ru.mousecray.endmagic.blocks.decorative.purpur.quartz;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import ru.mousecray.endmagic.blocks.base.BaseSlab;
import ru.mousecray.endmagic.init.EMBlocks;

public abstract class BlockPurpurQuartzSlab extends BaseSlab {

    public BlockPurpurQuartzSlab() {
        super(Material.ROCK, () -> EMBlocks.purpurSlabSingle, () -> EMBlocks.purpurSlabDouble);
        setSoundType(SoundType.STONE);
        setHardness(1.5F);
        setResistance(10.0F);
    }

    public static class BlockPurpurQuartzSlabDouble extends BlockPurpurQuartzSlab {
        public boolean isDouble() {
            return true;
        }
    }

    public static class BlockPurpurQuartzSlabSingle extends BlockPurpurQuartzSlab {
        public boolean isDouble() {
            return false;
        }
    }
}
