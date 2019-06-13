package ru.mousecray.endmagic.worldgen;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;
import java.util.stream.IntStream;

import static ru.mousecray.endmagic.worldgen.WorldGenDragonTree.findDirection;

public class WorldGenTest extends WorldGenAbstractTree {
    public WorldGenTest(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return findDirection(worldIn, position).map(direction -> {
            EnumFacing side = direction.rotateY();
            IntStream.rangeClosed(-1, 1).forEach(i ->
                    IntStream.rangeClosed(-1, 1).forEach(j ->
                            worldIn.setBlockState(
                                    position.offset(side, i).offset(EnumFacing.UP,j),
                                    Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED)
                            )));

            return true;
        }).orElse(false);
    }
}
