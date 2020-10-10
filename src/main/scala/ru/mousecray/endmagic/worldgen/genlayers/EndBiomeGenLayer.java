package ru.mousecray.endmagic.worldgen.genlayers;

import net.minecraft.world.gen.layer.GenLayer;

import javax.annotation.Nonnull;
import java.util.Random;

public class EndBiomeGenLayer extends GenLayer
{
    int biomeCount = 1;
    public EndBiomeGenLayer(long seed, int biomeCount)
    {
        super(seed);
        this.biomeCount = biomeCount;
    }

    @Nonnull
    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] res = new int[areaWidth * areaHeight];
        for(int x = 0; x < areaWidth; ++x)
        {
            for (int z = 0; z < areaHeight; z++)
            {
                res[z + x * areaWidth] = new Random().nextInt() % biomeCount;
            }
        }
        return res;
    }
}
