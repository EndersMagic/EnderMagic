package ru.mousecray.endmagic.world.genlayers;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import ru.mousecray.endmagic.Configuration;
import ru.mousecray.endmagic.init.EMBiomes;

public class GenLayerEndBiomes extends GenLayer
{
	private final int SKY_ID;
    private final int ICE_DESERT_ID;
	private final static int MAIN_ISLAND_SIZE;

	static
	{
		MAIN_ISLAND_SIZE = (int) (80 / Math.pow(2, (Configuration.endBiomeSize - 1)));
	}
	
    public GenLayerEndBiomes(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;  
		SKY_ID = Biome.getIdForBiome(Biomes.SKY);
        ICE_DESERT_ID = Biome.getIdForBiome(EMBiomes.ICE_DESERT);
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] inLayer = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] outLayer = IntCache.getIntCache(areaWidth * areaHeight);
    
        for (int i = 0; i < areaHeight; ++i)
        {
            for (int j = 0; j < areaWidth; ++j)
            {
            	this.initChunkSeed(j + areaX, i + areaY);
                int biomeInt = inLayer[j + i * areaWidth];

                if(biomeInt == 0 || (areaX < MAIN_ISLAND_SIZE && areaX > -MAIN_ISLAND_SIZE && areaY < MAIN_ISLAND_SIZE && areaY > -MAIN_ISLAND_SIZE))
                {
                	outLayer[j + i * areaWidth] = SKY_ID;
                }
                else if(biomeInt == 1)
                {
                    outLayer[j + i * areaWidth] = ICE_DESERT_ID;
                }
                else
                {
                	outLayer[j + i * areaWidth] = SKY_ID;
                }
            	
            }
            
        }
        
        return outLayer;
    
    }
	
}
