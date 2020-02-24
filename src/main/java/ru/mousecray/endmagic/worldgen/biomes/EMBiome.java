package ru.mousecray.endmagic.worldgen.biomes;

import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;

public interface EMBiome {
	public BiomeType getBiomeType();
	public Type getForgeTypeForBiome();
}