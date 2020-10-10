package ru.mousecray.endmagic.worldgen;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import ru.mousecray.endmagic.worldgen.genlayers.EndBiomeGenLayer;
import ru.mousecray.endmagic.worldgen.genlayers.UnityGenLayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EndBiomeProvider extends BiomeProvider
{
    private final List<Biome> biomes;
    public EndBiomeProvider(long seed, WorldType type, ArrayList<Biome> biomeIn)
    {
        biomes = new ArrayList<>();
        biomes.addAll(biomeIn);
        this.genBiomes = EndBiomeProvider.initLayers(seed, biomes);
        GenLayer[] agenlayer = this.getModdedBiomeGenerators(type, seed, new GenLayer[]{genBiomes});
        this.biomeIndexLayer = agenlayer[1];
        System.out.println(genBiomes.toString());
    }

    static GenLayer initLayers(long seed, List<Biome> biomes)
    {
        GenLayer parent = new EndBiomeGenLayer(seed, biomes.size());
        GenLayer layerZoom = new GenLayerZoom(seed, parent);
        return new UnityGenLayer(seed, biomes, layerZoom);
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();
        if (listToReuse == null || listToReuse.length < width * length)
        {
            listToReuse = new Biome[width * length];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
        {
            Biome[] abiome = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(abiome, 0, listToReuse, 0, width * length);
            return listToReuse;
        }
        else
        {
            int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);

            for (int i = 0; i < width * length; ++i)
            {
                listToReuse[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
            }

            return listToReuse;
        }
    }
}
