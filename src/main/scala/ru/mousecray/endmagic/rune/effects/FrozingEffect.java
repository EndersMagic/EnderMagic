package ru.mousecray.endmagic.rune.effects;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.rune.RuneEffect;

public class FrozingEffect extends RuneEffect {
    @Override
    public boolean isTickable() {
        return true;
    }

    @Override
    public void onUpdate(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower) {
        if (world.rand.nextInt((int) (200 / runePower)) == 0) {
            Block targetBlock = world.getBlockState(targetPos).getBlock();
            if (targetBlock == Blocks.WATER)
                world.setBlockState(targetPos, Blocks.ICE.getDefaultState());
            else if (targetBlock == Blocks.ICE && runePower > 1)
                world.setBlockState(targetPos, Blocks.PACKED_ICE.getDefaultState());
            else if (targetBlock == Blocks.LAVA && runePower > 5)
                world.setBlockState(targetPos, Blocks.OBSIDIAN.getDefaultState());
        }
    }
}
