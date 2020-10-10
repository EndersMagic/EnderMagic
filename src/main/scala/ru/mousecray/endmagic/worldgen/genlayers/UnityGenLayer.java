package ru.mousecray.endmagic.worldgen.genlayers;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class UnityGenLayer extends GenLayer
{
    List<Biome> biomes;
    public UnityGenLayer(long seed, List<Biome> diaomes, GenLayer parent)
    {
        super(seed);
        this.biomes = diaomes;
        this.parent = parent;
    }

    @Nonnull
    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] res = new int[areaWidth * areaHeight];
        int[] parent = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        ArrayList<Integer> ids = getIds(biomes);
        for(int x = 0; x < areaWidth; ++x)
            for (int z = 0; z < areaHeight; ++z)
                res[z + x * areaWidth] = ids.get(parent[z + x * areaWidth]);

        return res;
    }

    static ArrayList<Integer> getIds(List<Biome> biomes)
    {
        ArrayList<Integer> res = new ArrayList<>();
        for (Biome b: biomes)
            res.add(Biome.getIdForBiome(b));

        return res;
    }
}
