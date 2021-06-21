package ru.mousecray.endmagic.rune;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

public class RuneEffect<A> {

    public static RuneEffect EmptyEffect = new RuneEffect("empty");

    public boolean isValidTarget(IBlockState state, World world, BlockPos pos) {
        return true;
    }

    public double calculateRunePower(long averageInscribingTimeMillis) {
        System.out.println("inscribingTimeMillis " + averageInscribingTimeMillis);
        return 1d / averageInscribingTimeMillis;
    }

    public A createData(World world, BlockPos runePos, EnumFacing side, double runePower) {
        return null;
    }

    public void onInscribed(World world, BlockPos runePos, EnumFacing side, double runePower, A data) {
        onInscribed(world, runePos, side, runePos.offset(side), runePower, data);
    }

    public void onInscribed(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower, A data) {
        System.out.println("Rune inscribed! Power: " + runePower);
    }

    public void onNeighborChange(World world, BlockPos runePos, EnumFacing side, double runePower, A data) {
        onNeighborChange(world, runePos, side, runePos.offset(side), runePower, data);
    }

    public void onNeighborChange(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower, A data) {

    }

    public boolean isTickable() {
        return false;
    }

    public void onUpdate(World world, BlockPos runePos, EnumFacing side, double runePower, A data) {
        onUpdate(world, runePos, side, runePos.offset(side), runePower, data);
    }

    public void onUpdate(World world, BlockPos runePos, EnumFacing side, BlockPos targetPos, double runePower, A data) {
    }

    private String name;

    public RuneEffect() {
        name = NameAndTabUtils.getName(this);
    }

    public RuneEffect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
