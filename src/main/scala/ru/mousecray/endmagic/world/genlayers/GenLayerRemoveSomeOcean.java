package ru.mousecray.endmagic.world.genlayers;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRemoveSomeOcean extends GenLayer
{
    public GenLayerRemoveSomeOcean(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }

    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int i = areaX - 1;
        int j = areaY - 1;
        int k = areaWidth + 2;
        int l = areaHeight + 2;
        int[] inLayer = this.parent.getInts(i, j, k, l);
        int[] outLayer = IntCache.getIntCache(areaWidth * areaHeight);

        for (int z = 0; z < areaHeight; ++z)
        {
            for (int x = 0; x < areaWidth; ++x)
            {
                int north = inLayer[x + 1 + (z + 1 - 1) * (areaWidth + 2)];
                int east = inLayer[x + 1 + 1 + (z + 1) * (areaWidth + 2)];
                int west = inLayer[x + 1 - 1 + (z + 1) * (areaWidth + 2)];
                int south = inLayer[x + 1 + (z + 1 + 1) * (areaWidth + 2)];
                int current = inLayer[x + 1 + (z + 1) * k];
                outLayer[x + z * areaWidth] = current;
                this.initChunkSeed((long)(x + areaX), (long)(z + areaY));

                if (current == 0 && north == 0 && east == 0 && west == 0 && south == 0 && this.nextInt(10) == 0)
                {
                    outLayer[x + z * areaWidth] = 1;
                }
            }
        }

        return outLayer;
    }
}