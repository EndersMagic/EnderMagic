package ru.mousecray.endmagic.rune.effects;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.rune.RuneEffect;

public class LightRuneEffect extends RuneEffect {
    @Override
    public void onInscribed(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower) {
        onNeighborChange(world, runePos, side, targetPos, runePower);
    }

    @Override
    public void onNeighborChange(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower) {
        if (world.isAirBlock(targetPos))
            world.setBlockState(targetPos, EMBlocks.blockLight.getDefaultState());
    }
}
