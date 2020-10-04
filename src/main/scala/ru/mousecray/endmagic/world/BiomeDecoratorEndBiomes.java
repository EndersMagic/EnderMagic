package ru.mousecray.endmagic.world;

import net.minecraft.world.biome.BiomeEndDecorator;

public class BiomeDecoratorEndBiomes extends BiomeEndDecorator
{
    //нужно для генерации чего то типа кактусов
    public BiomeDecoratorEndBiomes()
    {
    }

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        super.genDecorations(biomeIn, worldIn, random);
    }
}
