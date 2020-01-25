package ru.mousecray.endmagic.capability.world;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhantomAvoidingGroupCapability {
    public Map<BlockPos, PhantomAvoidingGroup> groupAtPos = new HashMap<>();
    public Set<PhantomAvoidingGroup> allGroups = new HashSet<>();
}
