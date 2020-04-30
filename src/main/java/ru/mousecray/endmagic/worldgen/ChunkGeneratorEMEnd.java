package ru.mousecray.endmagic.worldgen;

import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.structure.MapGenEndCity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorEMEnd extends ChunkGeneratorEnd {

    public static final String END_CITY = "EndCity";
    public static final String IMMORTAL_TREE = "ImmortalTree";

    private final Random rand;
    private NoiseGeneratorOctaves lperlinNoise1;
    private NoiseGeneratorOctaves lperlinNoise2;
    private NoiseGeneratorOctaves perlinNoise1;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final BlockPos spawnPoint;
    private NoiseGeneratorSimplex islandNoise;
    private double[] buffer;
    private Biome[] biomesForGeneration;
    double[] pnr;
    double[] ar;
    double[] br;
    private final WorldGenEndIsland endIslands;
    private MapGenEndCity endCityGen;

    public ChunkGeneratorEMEnd(World world, boolean featuresEnabled, long seed,
                               BlockPos spawnPos) {
        super(world, featuresEnabled, seed, spawnPos);
        endCityGen = new MapGenEndCity(this);
        endIslands = new WorldGenEndIsland();
        this.world = world;
        mapFeaturesEnabled = featuresEnabled;
        spawnPoint = spawnPos;
        rand = new Random(seed);
        lperlinNoise1 = new NoiseGeneratorOctaves(rand, 16);
        lperlinNoise2 = new NoiseGeneratorOctaves(rand, 16);
        perlinNoise1 = new NoiseGeneratorOctaves(rand, 8);
        noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
        noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
        islandNoise = new NoiseGeneratorSimplex(rand);
        InitNoiseGensEvent.ContextEnd ctx = new InitNoiseGensEvent.ContextEnd(lperlinNoise1, lperlinNoise2,
                perlinNoise1, noiseGen5, noiseGen6, islandNoise);
        ctx = (InitNoiseGensEvent.ContextEnd) TerrainGen.getModdedNoiseGenerators(world, rand,
                (InitNoiseGensEvent.Context) ctx);
        lperlinNoise1 = ctx.getLPerlin1();
        lperlinNoise2 = ctx.getLPerlin2();
        perlinNoise1 = ctx.getPerlin();
        noiseGen5 = ctx.getDepth();
        noiseGen6 = ctx.getScale();
        islandNoise = ctx.getIsland();
        endCityGen = (MapGenEndCity) TerrainGen.getModdedMapGen(endCityGen,
                InitMapGenEvent.EventType.END_CITY);
    }

    @Override
    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        final int i = 2;
        final int j = 3;
        final int k = 33;
        final int l = 3;
        buffer = getHeights(buffer, x * 2, 0, z * 2, 3, 33, 3);
        for (int i2 = 0; i2 < 2; ++i2)
            for (int j2 = 0; j2 < 2; ++j2)
                for (int k2 = 0; k2 < 32; ++k2) {
                    final double d0 = 0.25;
                    double d2 = buffer[((i2 + 0) * 3 + j2 + 0) * 33 + k2 + 0];
                    double d3 = buffer[((i2 + 0) * 3 + j2 + 1) * 33 + k2 + 0];
                    double d4 = buffer[((i2 + 1) * 3 + j2 + 0) * 33 + k2 + 0];
                    double d5 = buffer[((i2 + 1) * 3 + j2 + 1) * 33 + k2 + 0];
                    double d6 = (buffer[((i2 + 0) * 3 + j2 + 0) * 33 + k2 + 1] - d2) * 0.25;
                    double d7 = (buffer[((i2 + 0) * 3 + j2 + 1) * 33 + k2 + 1] - d3) * 0.25;
                    double d8 = (buffer[((i2 + 1) * 3 + j2 + 0) * 33 + k2 + 1] - d4) * 0.25;
                    double d9 = (buffer[((i2 + 1) * 3 + j2 + 1) * 33 + k2 + 1] - d5) * 0.25;
                    for (int l2 = 0; l2 < 4; ++l2) {
                        final double d10 = 0.125;
                        double d11 = d2;
                        double d12 = d3;
                        double d13 = (d4 - d2) * 0.125;
                        double d14 = (d5 - d3) * 0.125;
                        for (int i3 = 0; i3 < 8; ++i3) {
                            final double d15 = 0.125;
                            double d16 = d11;
                            double d17 = (d12 - d11) * 0.125;
                            for (int j3 = 0; j3 < 8; ++j3) {
                                IBlockState iblockstate = AIR;
                                if (d16 > 0.0) iblockstate = END_STONE;
                                int k3 = i3 + i2 * 8;
                                int l3 = l2 + k2 * 4;
                                int i4 = j3 + j2 * 8;
                                primer.setBlockState(k3, l3, i4, iblockstate);
                                d16 += d17;
                            }
                            d11 += d13;
                            d12 += d14;
                        }
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                        d5 += d9;
                    }
                }
    }

    public void buildSurfaces(ChunkPrimer primer, int chunkX, int chunkZ, Biome[] biomeMap) {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, world)) return;
        for (int i = 0; i < 16; ++i)
            for (int j = 0; j < 16; ++j) {
                final int k = 1;
                int l = -1;
                Biome current = biomeMap[i + j * 16];
                IBlockState surface = END_STONE;
                IBlockState filler = END_STONE;
                if (current != Biomes.SKY) {
                    surface = current.topBlock;
                    filler = current.fillerBlock;
                }
                for (int i2 = 127; i2 >= 0; --i2) {
                    IBlockState iblockstate2 = primer.getBlockState(i, i2, j);
                    if (iblockstate2.getMaterial() == Material.AIR) l = -1;
                    else if (iblockstate2 == END_STONE) if (l == -1) {
                        l = 3 + rand.nextInt(2);
                        if (i2 >= 0) primer.setBlockState(i, i2, j, surface);
                        else primer.setBlockState(i, i2, j, filler);
                    } else if (l > 0) {
                        --l;
                        primer.setBlockState(i, i2, j, filler);
                    }
                }
            }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        rand.setSeed(x * 341873128712L + z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        biomesForGeneration = world.getBiomeProvider().getBiomes(biomesForGeneration, x * 16, z * 16, 16,
                16);
        setBlocksInChunk(x, z, chunkprimer);
        buildSurfaces(chunkprimer, x, z, biomesForGeneration);
        if (mapFeaturesEnabled) endCityGen.generate(world, x, z, chunkprimer);
        Chunk chunk = new Chunk(world, chunkprimer, x, z);
        byte[] biomeIds = chunk.getBiomeArray();
        for (int i = 0; i < biomeIds.length; ++i) biomeIds[i] = (byte) Biome.getIdForBiome(biomesForGeneration[i]);
        chunk.generateSkylightMap();
        return chunk;
    }

    private float getIslandHeightValue(int i1, int i2, int i3, int i4) {
        float f = (float) (i1 * 2 + i3);
        float f2 = (float) (i2 * 2 + i4);
        float f3 = 100.0f - MathHelper.sqrt(f * f + f2 * f2) * 8.0f;
        if (f3 > 80.0f) f3 = 80.0f;
        if (f3 < -100.0f) f3 = -100.0f;
        for (int i = -12; i <= 12; ++i)
            for (int j = -12; j <= 12; ++j) {
                long k = i1 + i;
                long l = i2 + j;
                if (k * k + l * l > 4096L && islandNoise.getValue((double) k, (double) l) < -0.8999999761581421) {
                    float f4 = (MathHelper.abs((float) k) * 3439.0f + MathHelper.abs((float) l) * 147.0f) % 13.0f
                            + 9.0f;
                    f = (float) (i3 - i * 2);
                    f2 = (float) (i4 - j * 2);
                    float f5 = 100.0f - MathHelper.sqrt(f * f + f2 * f2) * f4;
                    if (f5 > 80.0f) f5 = 80.0f;
                    if (f5 < -100.0f) f5 = -100.0f;
                    if (f5 > f3) f3 = f5;
                }
            }
        return f3;
    }

    @Override
    public boolean isIslandChunk(int x, int y) {
        return x * (long) x + y * (long) y > 4096L
                && getIslandHeightValue(x, y, 1, 1) >= 0.0f;
    }

    private double[] getHeights(double[] doubles, int i1, int i3, int i4, int i5, int i6, int i7) {
        ChunkGeneratorEvent.InitNoiseField event = new ChunkGeneratorEvent.InitNoiseField(this,
                doubles, i1, i3, i4, i5, i6, i7);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.DENY) return event.getNoisefield();
        if (doubles == null) doubles = new double[i5 * i6 * i7];
        double d0 = 684.412;
        final double d2 = 684.412;
        d0 *= 2.0;
        pnr = perlinNoise1.generateNoiseOctaves(pnr, i1, i3, i4, i5,
                i6, i7, d0 / 80.0, 4.277575000000001, d0 / 80.0);
        ar = lperlinNoise1.generateNoiseOctaves(ar, i1, i3, i4, i5,
                i6, i7, d0, 684.412, d0);
        br = lperlinNoise2.generateNoiseOctaves(br, i1, i3, i4, i5,
                i6, i7, d0, 684.412, d0);
        int i = i1 / 2;
        int j = i4 / 2;
        int k = 0;
        for (int l = 0; l < i5; ++l)
            for (int i2 = 0; i2 < i7; ++i2) {
                float f = getIslandHeightValue(i, j, l, i2);
                for (int j2 = 0; j2 < i6; ++j2) {
                    double d3 = ar[k] / 512.0;
                    double d4 = br[k] / 512.0;
                    double d5 = (pnr[k] / 10.0 + 1.0) / 2.0;
                    double d6;
                    if (d5 < 0.0) d6 = d3;
                    else if (d5 > 1.0) d6 = d4;
                    else d6 = d3 + (d4 - d3) * d5;
                    d6 -= 8.0;
                    d6 += f;
                    int k2 = 2;
                    if (j2 > i6 / 2 - k2) {
                        double d7 = (j2 - (i6 / 2 - k2)) / 64.0f;
                        d7 = MathHelper.clamp(d7, 0.0, 1.0);
                        d6 = d6 * (1.0 - d7) + -3000.0 * d7;
                    }
                    k2 = 8;
                    if (j2 < k2) {
                        double d8 = (k2 - j2) / (k2 - 1.0f);
                        d6 = d6 * (1.0 - d8) + -30.0 * d8;
                    }
                    doubles[k] = d6;
                    ++k;
                }
            }
        return doubles;
    }

    @Override
    public void populate(int x, int z) {
        ForgeEventFactory.onChunkPopulate(BlockFalling.fallInstantly = true, this, world,
                rand, x, z, false);
        BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
        if (mapFeaturesEnabled) endCityGen.generateStructure(world, rand, new ChunkPos(x, z));
        world.getBiome(blockpos.add(16, 0, 16)).decorate(world, world.rand, blockpos);
        long i = x * (long) x + z * (long) z;
        if (i > 4096L) {
            float f = getIslandHeightValue(x, z, 1, 1);
            if (f < -20.0f && rand.nextInt(14) == 0) {
                endIslands.generate(world, rand,
                        blockpos.add(rand.nextInt(16) + 8, 55 + rand.nextInt(16), rand.nextInt(16) + 8));
                if (rand.nextInt(4) == 0) endIslands.generate(world, rand, blockpos.add(rand.nextInt(16) + 8,
                        55 + rand.nextInt(16), rand.nextInt(16) + 8));
            }
            if (getIslandHeightValue(x, z, 1, 1) > 40.0f) {
                for (int j = rand.nextInt(5), k = 0; k < j; ++k) {
                    int l = rand.nextInt(16) + 8;
                    int i2 = rand.nextInt(16) + 8;
                    int j2 = world.getHeight(blockpos.add(l, 0, i2)).getY();
                    if (j2 > 0) {
                        int k2 = j2 - 1;
                        if (world.isAirBlock(blockpos.add(l, k2 + 1, i2))
                                && world.getBlockState(blockpos.add(l, k2, i2)).getBlock() == Blocks.END_STONE)
                            BlockChorusFlower.generatePlant(world, blockpos.add(l, k2 + 1, i2), rand, 8);
                    }
                }
                if (rand.nextInt(700) == 0) {
                    int l2 = rand.nextInt(16) + 8;
                    int i3 = rand.nextInt(16) + 8;
                    int j3 = world.getHeight(blockpos.add(l2, 0, i3)).getY();
                    if (j3 > 0) {
                        int k3 = j3 + 3 + rand.nextInt(7);
                        BlockPos blockpos2 = blockpos.add(l2, k3, i3);
                        new WorldGenEndGateway().generate(world, rand, blockpos2);
                        TileEntity tileentity = world.getTileEntity(blockpos2);
                        if (tileentity instanceof TileEntityEndGateway) {
                            TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway) tileentity;
                            tileentityendgateway.setExactPosition(spawnPoint);
                        }
                    }
                }
            }
        }
        ForgeEventFactory.onChunkPopulate(false, this, world, rand, x, z, false);
        BlockFalling.fallInstantly = false;
    }

    @Override
    //TODO: Structures
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Override
    @Nullable
    public BlockPos getNearestStructurePos(World world, String structureName, BlockPos position, boolean findUnexplored) {
        BlockPos pos = null;
        switch (structureName) {
            case END_CITY:
                if (endCityGen != null) pos = endCityGen.getNearestStructurePos(world, position, findUnexplored);
            case IMMORTAL_TREE:
            default:
        }
        return pos;
    }

    @Override
    public boolean isInsideStructure(World world, String structureName, BlockPos pos) {
        switch (structureName) {
            case END_CITY:
                return endCityGen != null && endCityGen.isInsideStructure(pos);
            case IMMORTAL_TREE:
            default:
        return false;
        }
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {}
}