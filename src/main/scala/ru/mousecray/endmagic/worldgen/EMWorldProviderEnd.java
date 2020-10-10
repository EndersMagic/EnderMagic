package ru.mousecray.endmagic.worldgen;

import net.minecraft.init.Biomes;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class EMWorldProviderEnd extends WorldProviderEnd
{
    public EMWorldProviderEnd()
    {
    }

    @Override
    public void init()
    {
        super.init();
        ArrayList<Biome> arr = new ArrayList<>();
        arr.add(Biomes.SKY);
        arr.add(Biomes.SAVANNA);
        this.biomeProvider = new EndBiomeProvider(world.getSeed(), WorldType.DEFAULT, arr);
    }
}
