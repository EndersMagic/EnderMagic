package ru.mousecray.endmagic.worldgen.plants;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenEnderPlants implements IWorldGenerator {
	
	private EndGrassGen endGrassGen = new EndGrassGen();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimensionType() == DimensionType.THE_END) {
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            if (!chunk.isEmpty()) {
            	for (int i = 0; i < 4; ++i) {
	            	int x = chunkX + random.nextInt(16);
	    			int y = random.nextInt(256);
	    			int z = chunkZ + random.nextInt(16);
	    			BlockPos pos = new BlockPos(x+8, y, z+8);
	                if (world.isAirBlock(pos)) endGrassGen.generate(world, random, new BlockPos(x, y, z));
            	}
            }
        }
	}
}