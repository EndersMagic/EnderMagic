package ru.mousecray.endmagic.worldgen.biomes;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary.Type;

public abstract class EMBiome extends Biome {

	public EMBiome(BiomeProperties properties) {
		super(properties);
		
	}

	public abstract ImmutableList<Type> getForgeTypesForBiome();
}