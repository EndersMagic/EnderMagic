package ru.mousecray.endmagic.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.mousecray.endmagic.init.EMBiomes;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.Random;

public class WorldGenSurfacePatch implements IWorldGenerator
{
    private final IBlockState surfaceBlockState;
    private final IBlockState replacementBlockState;
    private final int numPatches;
    private final Random randy;

    public WorldGenSurfacePatch(IBlockState generate, IBlockState replace, int numPatches)
    {
        this.surfaceBlockState = generate;
        this.replacementBlockState = replace;
        this.numPatches = numPatches;
        randy = new Random(8008135);
    }


    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16)) == EMBiomes.ICE_DESERT) return;
        boolean[][] previouslyChecked = new boolean[16][16];
        if(rand.nextInt(16) != 15) return;
        for(int n = 0; n < numPatches; n++)
        {
            int size = 10 + randy.nextInt(5);
            int maxOffset = 16 - size;
            if(maxOffset == 0)
                maxOffset = 1;

            int xOffset = randy.nextInt(maxOffset);
            int zOffset = randy.nextInt(maxOffset);
            double midX = (size / 2.0 + xOffset);
            double midZ = (size / 2.0 + zOffset);
            double maxDist = (size / 2.0) * (size / 2.0);

            for(int x=0; x<16; x++)
            {
                for(int z=0; z<16; z++)
                {
                    if(!previouslyChecked[z][x])
                    {
                        //distort the actual distance a bit so we dont generate perfect circles
                        double xWarp = (randy.nextDouble() / 3.0) + 0.833;
                        double zWarp = (randy.nextDouble() / 3.0) + 0.833;
                        double xDist = (midX - x) * xWarp;
                        double zDist = (midZ - z) * zWarp;
                        if(xDist * xDist + zDist * zDist < maxDist - rand.nextInt(3))
                        {
                            //mark this block as already decorated so we dont waste time trying to decorate it again
                            previouslyChecked[z][x] = true;

                            BlockPos current = new BlockPos(chunkX * 16, 0, chunkZ * 16).add(x, 70, z);

                            //find the surface block
                            for (; current.getY() > 49 && world.isAirBlock(current); current = current.down()) { }

                            if(world.getBlockState(current) == replacementBlockState)
                            {
                                world.setBlockState(current, surfaceBlockState, 2);
                                if(world.isAirBlock(current.up()))
                                {
                                    if (rand.nextInt(5) == 1)
                                    {
                                        world.setBlockState(current.up(), EMBlocks.enderTallgrass.getDefaultState());
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
