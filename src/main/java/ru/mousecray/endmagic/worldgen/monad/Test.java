package ru.mousecray.endmagic.worldgen.monad;

import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.stream.Collectors;

import static ru.mousecray.endmagic.worldgen.monad.WorldIO.sequence;
import static ru.mousecray.endmagic.worldgen.monad.WorldIO.setBlockState;

public class Test {

    public static ImmutableList<BlockPos> portalCoords = ImmutableList.of(/*тут задаешь смещения для координат портла*/);

    public static WorldIO<WorldAndChunkCoord, Void> generateObsidian =
            WorldIO.<WorldAndChunkCoord>start()
                    .andThen(worldAndChunk -> {
                        int chunkX = worldAndChunk.chunkX;
                        int chunkZ = worldAndChunk.chunkZ;
                        World world = worldAndChunk.world;
                        int x = chunkX * 16 + world.rand.nextInt(16) + 8;
                        int z = chunkZ * 16 + world.rand.nextInt(16) + 8;
                        return new BlockPos(x, 0, z);

                    })
                    .flatMap(WorldIO::getTopPos)
                    .flatMap(topPos ->
                            sequence(portalCoords
                                    .stream()
                                    .map(offset -> setBlockState(topPos.add(offset), Blocks.OBSIDIAN.getDefaultState()))
                                    .collect(Collectors.toList()))
                    );

}
