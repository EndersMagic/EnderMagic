package ru.mousecray.endmagic.worldgen;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenEnderTrees implements IWorldGenerator {

    private WorldGenDragonTreeWorld dragonGenerator = new WorldGenDragonTreeWorld();
    private WorldGenNaturalTreeWorld naturalGenerator = new WorldGenNaturalTreeWorld();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimensionType() == DimensionType.THE_END) {
            dragonGenerator.generateWorld(random, chunkX, chunkZ, world);
            naturalGenerator.generateWorld(random, chunkX, chunkZ, world);
        }
    }
}
