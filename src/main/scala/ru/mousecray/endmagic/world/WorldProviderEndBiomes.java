package ru.mousecray.endmagic.world;

import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderEndBiomes extends WorldProviderEnd 
{

	@Override
	public void init()
    {
		this.biomeProvider = new EndBiomeProvider(world.getSeed(), world.getWorldType());
    }

	@Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorEndBiomes(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed(), this.getSpawnCoordinate());
    }
}
