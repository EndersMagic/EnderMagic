package ru.mousecray.endmagic.worldgen.biomes;

import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;

public class BiomeProviderEMEnd extends BiomeProvider {
	private GenLayer genBiomes;
	private GenLayer biomeIndexLayer;
	private final BiomeCache biomeCache;

	public BiomeProviderEMEnd() {
		this.biomeCache = new BiomeCache(this);
	}
}