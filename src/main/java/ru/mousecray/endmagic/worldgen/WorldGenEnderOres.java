package ru.mousecray.endmagic.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.mousecray.endmagic.worldgen.ores.WorldGenEnderOre;

public class WorldGenEnderOres implements IWorldGenerator {
	
	WorldGenEnderOre enderOre = new WorldGenEnderOre();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {	
		if (world.provider.getDimensionType() == DimensionType.THE_END) {
			Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
			if (!chunk.isEmpty()) {
				int x = chunkX << 4;
				int z = chunkZ << 4;
				BlockPos position = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).down();
				if (!world.isAirBlock(position)) {
					int chance = random.nextInt(100);
					if (chance > 95)  {
						enderOre.setNumberOfBlocks(7);
						enderOre.generate(world, random, position);
					}
					else if (chance > 70) {
						enderOre.setNumberOfBlocks(5);
						enderOre.generate(world, random, position);
					}
					else if (chance > 50){
						enderOre.setNumberOfBlocks(3);
						enderOre.generate(world, random, position);
					}
				}
			}
		}
	}
}