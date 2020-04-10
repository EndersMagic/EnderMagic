package ru.mousecray.endmagic.util.worldgen;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorldGenUtils {
    public static void generateInAreaBreakly(BlockPos start, BlockPos end, Predicate<BlockPos> generate) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int z = start.getZ(); z <= end.getZ(); z++) {
                for (int y = start.getY(); y <= end.getY(); y++) {
                    pos.setPos(x, y, z);
                    if (!generate.test(pos))
                        return;
                }
            }
        }
    }

    public static void generateInArea(BlockPos start, BlockPos end, Consumer<BlockPos> generate) {
        generateInAreaBreakly(start, end, pos -> {
            generate.accept(pos);
            return true;
        });
    }

    public static boolean areaAvailable(World worldIn, BlockPos start, BlockPos end, Set<Block> acceptedBlocks) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int z = start.getZ(); z <= end.getZ(); z++) {
                for (int y = start.getY(); y <= end.getY(); y++) {
                    pos.setPos(x, y, z);
                    if (!acceptedBlocks.contains(worldIn.getBlockState(pos).getBlock()))
                        return false;
                }
            }
        }
        return true;
    }

    public static boolean areaAvailable(World worldIn, BlockPos start, BlockPos end) {
        return areaAvailable(worldIn, start, end, ImmutableSet.of(Blocks.AIR));
    }
    
    public static void setBlockAndNotifyAdequately(boolean notify, World world, BlockPos pos, IBlockState state) {
        if (notify) world.setBlockState(pos, state, 3);
        else {
            int flag = net.minecraftforge.common.ForgeModContainer.fixVanillaCascading ? 2| 16 : 2; //Forge: With bit 5 unset, it will notify neighbors and load adjacent chunks.
            world.setBlockState(pos, state, flag);
        }
    }
}
