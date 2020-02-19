package ru.mousecray.endmagic.worldgen.trees.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ru.mousecray.endmagic.worldgen.trees.WorldGenPhantomTree;

public class WorldGenPhantomTreeWorld {
	WorldGenPhantomTree treeGen = new WorldGenPhantomTree(false);
	
	public void generateWorld(Random random, int chunkX, int chunkZ, World world) {
		int lchunkX = chunkX << 4;
		int lchunkZ = chunkZ << 4;
		Chunk chunk = world.getChunkFromChunkCoords(lchunkX, lchunkZ);
		if (random.nextInt(20) == 0 && !chunk.isEmpty()) {
				int x = lchunkX + random.nextInt(16);
				int z = lchunkZ + random.nextInt(16);
				BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x + 8, 0, z + 8));
				treeGen.generate(world, random, pos);
		}
	}
}