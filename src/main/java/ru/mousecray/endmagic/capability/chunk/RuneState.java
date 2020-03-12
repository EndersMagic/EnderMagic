package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.EnumFacing;

import java.util.EnumMap;
import java.util.Map;

public class RuneState {

    public Map<EnumFacing, Rune> runesOnSides;

    public RuneState() {
        runesOnSides = new EnumMap<>(EnumFacing.class);
        for (EnumFacing facing : EnumFacing.values())
            runesOnSides.put(facing, new Rune());
    }
}
