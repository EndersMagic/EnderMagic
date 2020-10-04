package ru.mousecray.endmagic.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.structure.MapGenEndCity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class ChunkGeneratorEndBiomes extends ChunkGeneratorEnd 
{
    /** RNG. */
    private final Random rand;
    protected static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    /** Reference   to the World object. */
    private final World world;
    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean mapFeaturesEnabled;
    private MapGenEndCity endCityGen = new MapGenEndCity(this);
    private NoiseGeneratorSimplex islandNoise;
    /** The biomes that are used to generate the chunk */
    private Biome[] biomesForGeneration;
    double[] pnr;
    double[] ar;
    double[] br;
    private final WorldGenEndIsland endIslands = new WorldGenEndIsland();

    public ChunkGeneratorEndBiomes(World world, boolean featuresEnabled, long seed, BlockPos spawnPos)
    {
    	super(world, featuresEnabled, seed, spawnPos);
        this.world = world;
        this.mapFeaturesEnabled = featuresEnabled;
        this.rand = new Random(seed);
        this.islandNoise = new NoiseGeneratorSimplex(this.rand);
        this.endCityGen = (MapGenEndCity)  TerrainGen.getModdedMapGen(this.endCityGen, InitMapGenEvent.EventType.END_CITY);
    }

    public void buildSurfaces(ChunkPrimer primer, int chunkX, int chunkZ, Biome[] biomeMap)//важно - есть изменения
    {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, this.world)) return;
        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                int l = -1;
                Biome current = biomeMap[i + j * 16]; //some backwards ass shit
                IBlockState surface = END_STONE;
                IBlockState filler = END_STONE;
                if(current != Biomes.SKY)
                {
                	surface = current.topBlock;
                	filler = current.fillerBlock;
                }
                

                for (int i1 = 127; i1 >= 0; --i1)
                {
                    IBlockState iblockstate2 = primer.getBlockState(i, i1, j);

                    if (iblockstate2.getMaterial() == Material.AIR)
                    {
                        l = -1;
                    }
                    else if (iblockstate2 == END_STONE)
                    {
                        if (l == -1)
                        {
                            l = rand.nextInt(2) + 3;

                            if (i1 >= 0) primer.setBlockState(i, i1, j, surface);
                            else         primer.setBlockState(i, i1, j, filler);
                        }
                        else if (l > 0)
                        {
                            --l;
                            primer.setBlockState(i, i1, j, filler);
                        }
                    }
                }

                for (int i1 = 0; i1 <= 127; ++i1)
                {
                    IBlockState iblockstate2 = primer.getBlockState(i, i1, j);
                    if (iblockstate2.getMaterial() == Material.AIR)
                    {
                        l = -1;
                    }
                    else if (iblockstate2 == END_STONE)
                    {
                        if (l == -1)
                        {
                            l = rand.nextInt(2) + 3;

                            if (i1 >= 0) primer.setBlockState(i, i1, j, filler);
                        }
                        else if (l > 0)
                        {
                            --l;
                        }
                    }
                }

            }
        }
    }

    /**
     * Generates the chunk at the specified position, from scratch
     */
    public Chunk generateChunk(int x, int z) //важно
    {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.setBlocksInChunk(x, z, chunkprimer);
        this.buildSurfaces(chunkprimer, x, z, biomesForGeneration);

        if (this.mapFeaturesEnabled)
        {
            this.endCityGen.generate(this.world, x, z, chunkprimer);
           // this.endGenCorn.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }



    public float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_)//делаем публичным, потмо заменю АТ
    {
        float f = (float)(p_185960_1_ * 2 + p_185960_3_);
        float f1 = (float)(p_185960_2_ * 2 + p_185960_4_);
        float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;

        if (f2 > 80.0F)
        {
            f2 = 80.0F;
        }

        if (f2 < -100.0F)
        {
            f2 = -100.0F;
        }

        for (int i = -12; i <= 12; ++i)
        {
            for (int j = -12; j <= 12; ++j)
            {
                long k = p_185960_1_ + i;
                long l = p_185960_2_ + j;

                if (k * k + l * l > 4096L && this.islandNoise.getValue((double)k, (double)l) < -0.8999999761581421D)
                {
                    float f3 = (MathHelper.abs((float)k) * 3439.0F + MathHelper.abs((float)l) * 147.0F) % 13.0F + 9.0F;
                    f = (float)(p_185960_3_ - i * 2);
                    f1 = (float)(p_185960_4_ - j * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * f3;

                    if (f4 > 80.0F)
                    {
                        f4 = 80.0F;
                    }

                    if (f4 < -100.0F)
                    {
                        f4 = -100.0F;
                    }

                    if (f4 > f2)
                    {
                        f2 = f4;
                    }
                }
            }
        }

        return f2;
    }
}
