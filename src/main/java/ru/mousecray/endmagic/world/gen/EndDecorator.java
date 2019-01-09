package ru.mousecray.endmagic.world.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.mousecray.endmagic.init.EMBlocks;

public class EndDecorator implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.getDimensionType().equals(DimensionType.THE_END))
		{
			this.generateOre(world, random, EMBlocks.enderOre.getDefaultState(), Blocks.END_STONE.getDefaultState(), 4, chunkX, chunkZ, 24, 0, 256);
		}
	}

	
	private void generateOre(World world, Random rand, IBlockState ore, IBlockState stone, int blockAmount, int chunkX, int chunkZ, int amountPerChunk, int minY, int maxY)
	{		
		WorldGenMinable generator = new WorldGenMinable(ore, blockAmount, BlockMatcher.forBlock(stone.getBlock()));
		
		for (int var5 = 0; var5 < amountPerChunk; ++var5)
        {
			final int var6 = chunkX * 16 + rand.nextInt(16);
            final int var7 = rand.nextInt(maxY - minY) + minY;
            final int var8 = chunkZ * 16 + rand.nextInt(16);
            
            BlockPos pos = new BlockPos(var6, var7, var8);        
            generator.generate(world, rand, pos);
        }
	}
}
