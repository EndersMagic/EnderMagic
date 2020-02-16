package ru.mousecray.endmagic.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldGenNaturalTreeWorld {
	
	WorldGenNaturalTree treeGen = new WorldGenNaturalTree(false);

	public void generateWorld(Random random, int chunkX, int chunkZ, World world) {
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		if (!chunk.isEmpty()) {
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(70);
			int z = chunkZ + random.nextInt(16);
			BlockPos pos = new BlockPos(x + 8, y, z + 8);
			if (world.isAirBlock(pos)) treeGen.generate(world, random, pos.down());
		}
	}
}