package ru.mousecray.endmagic.worldgen.monad;

import net.minecraft.world.World;

public class WorldAndChunkCoord extends OnlyWorld {
    public final int chunkX;
    public final int chunkZ;

    public WorldAndChunkCoord(World world, int chunkZ, int chunkX) {
        super(world);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }
}
