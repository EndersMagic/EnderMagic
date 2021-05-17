package ru.mousecray.endmagic.rune;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneEffect {

    public static RuneEffect EmptyEffect = new RuneEffect();

    public boolean isValidTarget(IBlockState state, World world, BlockPos pos) {
        return true;
    }

    public double calculateRunePower(long inscribingTimeMillis) {
        return 1000d * 60 / inscribingTimeMillis; // 60 sec - 1 power
    }

    public void onInscribed(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower) {
        System.out.println("Rune inscribed!");
    }

    public void onNeighborChange(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower) {
    }

    public boolean isTickable() {
        return false;
    }

    public void onUpdate(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower) {
    }
}
