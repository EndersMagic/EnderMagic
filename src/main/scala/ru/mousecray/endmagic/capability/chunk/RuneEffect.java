package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneEffect {

    public static RuneEffect EmptyEffect = new RuneEffect();

    public void onInscribed(World world, BlockPos pos, EnumFacing side) {
        System.out.println("Rune inscribed!");

    }
}
