package ru.mousecray.endmagic.rune.effects;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.rune.RuneEffect;
import ru.mousecray.endmagic.teleport.Location;

public class RedTransposEffect extends RuneEffect<Location> {

    @Override
    public Location createData(World world, BlockPos runePos, EnumFacing side, double runePower) {
        return Location.spawn;
    }

    @Override
    public void onNeighborChange(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower, Location data) {
        int redstonePower = world.getRedstonePower(runePos, side);
        System.out.println("redstonePower " + redstonePower);
    }
}
