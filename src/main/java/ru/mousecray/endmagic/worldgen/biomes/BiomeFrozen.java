package ru.mousecray.endmagic.worldgen.biomes;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeFrozen extends EMBiome {

	public BiomeFrozen(BiomeProperties properties) {
		super(properties);
	}

	@Override
	public ImmutableList<Type> getForgeTypesForBiome() {
		return ImmutableList.of(Type.COLD, Type.END, Type.RARE, Type.FOREST);
	}

}