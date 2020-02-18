package ru.mousecray.endmagic.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldGenNaturalTreeWorld {
	
	WorldGenNaturalTree treeGen = new WorldGenNaturalTree(false);

	public void generateWorld(Random random, int chunkX, int chunkZ, World world) {
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		if (random.nextInt(10) == 0 && !chunk.isEmpty()) {
				int x = random.nextInt(16);
				int z = random.nextInt(16);
				BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(chunkX + x + 8, 0, chunkZ + z + 8)).down();
				treeGen.generate(world, random, pos);
		}
	}
}