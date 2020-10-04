package ru.mousecray.endmagic.world.biomes;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeEnd;
import ru.mousecray.endmagic.world.BiomeDecoratorEndBiomes;

public class BiomeEndIceDesert extends BiomeEnd
{
    public static BiomeProperties properties = new BiomeProperties("Ice desert of end");
    static {
        properties.setTemperature(Biomes.SKY.getDefaultTemperature());
        properties.setRainfall(Biomes.SKY.getRainfall());
        properties.setRainDisabled();
    }
    public BiomeEndIceDesert()
    {
        super(properties);
        fillerBlock = Blocks.PACKED_ICE.getDefaultState();
        topBlock = Blocks.ICE.getDefaultState();
    }

    @Override
    public BiomeDecorator createBiomeDecorator(){ return new BiomeDecoratorEndBiomes(); }
}
