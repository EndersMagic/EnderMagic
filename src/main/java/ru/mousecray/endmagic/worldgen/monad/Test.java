package ru.mousecray.endmagic.worldgen.monad;

import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.stream.Collectors;

import static ru.mousecray.endmagic.worldgen.monad.WorldIO.*;

public class Test {

    public static ImmutableList<BlockPos> portalCoords = ImmutableList.of(/*тут задаешь смещения для координат портла*/);

    public static WorldIO<Void> generateObsidian =
            WorldIO.<WorldAndChunkCoord>start()
                    .andThen((world, chunkX, chunkZ) -> {
                        int x = chunkX * 16 + world.rand.nextInt(16) + 8;
                        int z = chunkZ * 16 + world.rand.nextInt(16) + 8;
                        return new BlockPos(x, 0, z);
                    })
                    .flatMap(WorldIO::getTopPos)
                    .flatMap(topPos -> getBlockState(topPos)
                            .flatMap(state ->
                                    when(state.getBlock() == Blocks.GRASS,
                                            sequence(portalCoords
                                                    .stream()
                                                    .map(offset -> setBlockState(topPos.add(offset), Blocks.OBSIDIAN.getDefaultState()))
                                                    .collect(Collectors.toList()))
                                    )
                            )
                    );

}
