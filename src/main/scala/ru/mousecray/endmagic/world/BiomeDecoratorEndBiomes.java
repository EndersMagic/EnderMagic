package ru.mousecray.endmagic.world;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEndDecorator;

import java.util.Random;

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
