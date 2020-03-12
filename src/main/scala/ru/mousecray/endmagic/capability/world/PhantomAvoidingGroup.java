package ru.mousecray.endmagic.capability.world;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class PhantomAvoidingGroup {
    public List<BlockPos> blocks = new ArrayList<>();
    public boolean avoidingStarted = false;
    public int avoidTicks = 0;
    public int increment = 1;
}
