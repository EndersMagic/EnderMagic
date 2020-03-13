package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumMap;
import java.util.Map;

public class RuneState {
    private final Map<EnumFacing, Rune> runesOnSides = new EnumMap<>(EnumFacing.class);
    private final BlockPos pos;

    public RuneState(BlockPos pos) {
        this.pos = pos;
    }

    public Rune getRuneAtSide(EnumFacing facing) {
        return runesOnSides.computeIfAbsent(facing, __ -> new Rune(pos));
    }
}
