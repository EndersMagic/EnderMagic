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
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimensionType() == DimensionType.THE_END) {
			Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
			if (random.nextInt(10) == 0 && !chunk.isEmpty()) {
				int x = chunkX << 4;
				int z = chunkZ << 4;
				BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).down();
				if (!world.isAirBlock(pos)) endGrassGen.generate(world, random, pos);
			}
		}
	}
}