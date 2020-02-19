package ru.mousecray.endmagic.worldgen.trees.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ru.mousecray.endmagic.worldgen.trees.WorldGenPhantomTree;

import java.util.Random;

import static ru.mousecray.endmagic.worldgen.trees.world.WorldGenDragonTreeWorld.centralIslandSize;

public class WorldGenPhantomTreeWorld {
    WorldGenPhantomTree treeGen = new WorldGenPhantomTree(false);

    public void generateWorld(Random random, int chunkX, int chunkZ, World world) {
        if (chunkX * chunkX + chunkZ * chunkZ > centralIslandSize * centralIslandSize) {
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
}