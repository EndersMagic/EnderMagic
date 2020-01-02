package ru.mousecray.endmagic.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.Random;

public class WorldGenPhantomTree extends WorldGenAbstractTree {
    public WorldGenPhantomTree(boolean notify) {
        super(notify);
    }

    private IBlockState phantomLog = EMBlocks.enderLog.stateWithBlockType(EnderBlockTypes.EnderTreeType.PHANTOM);
    private IBlockState phantomLeaves = EMBlocks.enderLeaves.stateWithBlockType(EnderBlockTypes.EnderTreeType.PHANTOM);
    private IBlockState air = Blocks.AIR.getDefaultState();

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (areaAvailable(worldIn, position.add(-3, 1, -3), position.add(3, 9, 3))) {
            for (int y = 0; y < 5; y++) {
                worldIn.setBlockState(position.add(0, y, 0), phantomLog);
            }
            WorldGenUtils.generateInArea(position.add(-2, 5, -2), position.add(2, 7, 2), pos -> worldIn.setBlockState(pos, phantomLeaves));
            WorldGenUtils.generateInArea(position.add(-2, 8, -2), position.add(2, 8, 2), pos -> {
                if (rand.nextFloat() > 3 / 25)
                    worldIn.setBlockState(pos, phantomLeaves);
            });
            WorldGenUtils.generateInArea(position.add(-1, 9, -1), position.add(1, 9, 1), pos -> {
                if (rand.nextFloat() > 2 / 9)
                    worldIn.setBlockState(pos, phantomLeaves);
            });

        } else
            return false;
    }

    private static boolean areaAvailable(World worldIn, BlockPos start, BlockPos end) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int z = start.getZ(); z <= end.getZ(); z++) {
                for (int y = start.getY(); y <= end.getY(); y++) {
                    pos.setPos(x, y, z);
                    if (!worldIn.isAirBlock(pos))
                        return false;
                }
            }
        }
        return true;
    }

    private IBlockState[][] variant1WeepingLeaves = new IBlockState[][]{
            {phantomLeaves, air,            phantomLeaves,  phantomLeaves,  air},
            {phantomLeaves, phantomLeaves,  air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,  air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,  air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,  air,            air,            phantomLeaves},
            {air,           phantomLeaves,  air,            air,            phantomLeaves}
    };

    private IBlockState[][] variant2WeepingLeaves = new IBlockState[][]{
            {phantomLeaves, phantomLeaves,  air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,  phantomLeaves,  phantomLeaves,  phantomLeaves},
            {phantomLeaves, air,            phantomLeaves,  air,            air},
            {phantomLeaves, air,            phantomLeaves,  air,            air},
            {air,           air,            phantomLeaves,  air,            air},
            {air,           air,            air,            air,            air}
    };

    private IBlockState[][] variant3WeepingLeaves = new IBlockState[][]{
            {phantomLeaves, air,            air,    phantomLeaves,  air},
            {phantomLeaves, phantomLeaves,  air,    phantomLeaves,  phantomLeaves},
            {air,           phantomLeaves,  air,    air,            phantomLeaves},
            {air,           phantomLeaves,  air,    air,            phantomLeaves},
            {air,           phantomLeaves,  air,    air,            phantomLeaves},
            {air,           phantomLeaves,  air,    air,            phantomLeaves},
    };
}
