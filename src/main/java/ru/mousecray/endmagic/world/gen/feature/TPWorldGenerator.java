package ru.mousecray.endmagic.world.gen.feature;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class TPWorldGenerator implements IWorldGenerator {

	public static WorldGenerator 
	DRAGON_TREE = new DragonTreeGen(false),
	NATURAL_TREE = new NaturalTreeGen(false),
	ENDER_FARM = new EnderFarmGen();
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
			//Surface
			case 0:
				break;
			//End
			case 1:
				generateEnderStructure(world, chunkX*16, chunkZ*16, 3, rand, DRAGON_TREE);
				generateEnderStructure(world, chunkX*16, chunkZ*16, 3, rand, NATURAL_TREE);
				generateEnderStructure(world, chunkX*16, chunkZ*16, 4, rand, ENDER_FARM);
				break;
			//Nether
			case -1:	
		}
	}
	
	private void generateEnderStructure(World world, int k, int j, int chance, Random rand, WorldGenerator tree) {
		for(int i = 0; i < chance; i++) {
			int x = k + rand.nextInt(16);
			int y = rand.nextInt(256);
			int z = j + rand.nextInt(16);
			BlockPos pos = new BlockPos(x+8, y, z+8);
			if(world.isAirBlock(pos)) {
				tree.generate(world, rand, pos);
			}
	    }
	}
}