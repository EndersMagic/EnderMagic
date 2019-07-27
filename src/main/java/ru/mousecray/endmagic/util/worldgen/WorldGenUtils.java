package ru.mousecray.endmagic.util.worldgen;

import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class WorldGenUtils {
    public static void generateInArea(BlockPos start, BlockPos end, Consumer<BlockPos> generate) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int z = start.getZ(); z <= end.getZ(); z++) {
                for (int y = start.getY(); y <= end.getY(); y++) {
                    pos.setPos(x, y, z);
                    generate.accept(pos);
                }
            }
        }
    }
}
