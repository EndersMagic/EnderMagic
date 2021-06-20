package ru.mousecray.endmagic.capability.chunk.portal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Map;

public class PortalCapability {
    public final Chunk chunk;
    public Map<BlockPos, Integer> masterPosToHeight = new HashMap<>();

    public PortalCapability(Chunk chunk) {
        this.chunk = chunk;
    }
}
