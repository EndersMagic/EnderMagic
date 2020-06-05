package ru.mousecray.endmagic.world;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.mousecray.endmagic.world.biomes.BiomeEndIceDesert;

public class BiomeRegistrar 
{
	public static final Biome ICE_DESERT = new BiomeEndIceDesert();
	
	public static void registerBiomes()
	{
		initBiome(ICE_DESERT,"ice_desert", Type.END);
	}
	
	private static void initBiome(Biome biome, String name, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
	}
}
