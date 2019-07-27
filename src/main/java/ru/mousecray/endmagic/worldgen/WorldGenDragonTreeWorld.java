package ru.mousecray.endmagic.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.HashSet;
import java.util.Random;

public class WorldGenDragonTreeWorld {

    WorldGenDragonTree treeGen = new WorldGenDragonTree(false);

    public static int centralIslandSize = 9;
    //3;minecraft:bedrock,2*minecraft:dirt,minecraft:end_portal;1;

    public void generateWorld(Random random, int chunkX, int chunkZ, World world) {
        if (chunkX * chunkX + chunkZ * chunkZ < centralIslandSize * centralIslandSize) {
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            if (!chunk.isEmpty()) {
                int startX = chunkX << 4;
                int startZ = chunkZ << 4;
                HashSet<BlockPos> alreadyChecked = new HashSet<>();
                WorldGenUtils.generateInArea(
                        new BlockPos(startX, 30, startZ),
                        new BlockPos(startX + 15, 50, startZ + 15),
                        pos -> {
                            if (random.nextInt(40) == 0)
                                treeGen.generate(world, random, pos, alreadyChecked);

                            alreadyChecked.clear();
                        }
                );
            }
        }
    }


}
