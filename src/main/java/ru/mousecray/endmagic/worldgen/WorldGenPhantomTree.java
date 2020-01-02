package ru.mousecray.endmagic.worldgen;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.Vec2i;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.ArrayList;
import java.util.Collections;
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
            WorldGenUtils.generateInArea(position.add(-2, 5, -2), position.add(2, 6, 2), pos -> worldIn.setBlockState(pos, phantomLeaves));
            WorldGenUtils.generateInArea(position.add(-2, 7, -2), position.add(2, 7, 2), pos -> {
                if (rand.nextFloat() > 3f / 25)
                    worldIn.setBlockState(pos, phantomLeaves);
            });
            int c=0;
            WorldGenUtils.generateInArea(position.add(-1, 8, -1), position.add(1, 8, 1), pos -> {
                if (rand.nextFloat() > 2f / 9)
                    worldIn.setBlockState(pos, phantomLeaves);
                else
                    c++;
            });
            ArrayList<IBlockState[][]> list = Lists.newArrayList(variant1WeepingLeaves, variant2WeepingLeaves, variant3WeepingLeaves, variant4WeepingLeaves);
            Collections.shuffle(list);

            //idea: генерировать вертикальные полосы листвы в точках

            boolean inverted = rand.nextBoolean();
            generatePlaneWithAxis(worldIn, position.add(3, 6, inverted ? -2 : 2), new Vec2i(0, inverted ? 1 : -1), list.get(0));

            inverted = rand.nextBoolean();
            generatePlaneWithAxis(worldIn, position.add(inverted ? -2 : 2, 6, 3), new Vec2i(inverted ? 1 : -1, 0), list.get(1));

            inverted = rand.nextBoolean();
            generatePlaneWithAxis(worldIn, position.add(-3, 6, inverted ? -2 : 2), new Vec2i(0, inverted ? 1 : -1), list.get(2));

            inverted = rand.nextBoolean();
            generatePlaneWithAxis(worldIn, position.add(inverted ? -2 : 2, 6, -3), new Vec2i(inverted ? 1 : -1, 0), list.get(3));

            return true;

        } else
            return false;
    }

    private void generatePlaneWithAxis(World world, BlockPos start, Vec2i vec2i, IBlockState[][] iBlockStates) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int y = 0; y < iBlockStates.length; y++) {
            for (int horizontal = 0; horizontal < iBlockStates[y].length; horizontal++) {
                pos.setPos(start.getX() + vec2i.x * horizontal, start.getY() - y, start.getZ() + vec2i.y * horizontal);
                world.setBlockState(pos, iBlockStates[y][horizontal]);
            }
        }
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
            {phantomLeaves, air, phantomLeaves, phantomLeaves, air},
            {phantomLeaves, phantomLeaves, air, phantomLeaves, phantomLeaves},
            {phantomLeaves, phantomLeaves, air, phantomLeaves, phantomLeaves},
            {phantomLeaves, phantomLeaves, air, phantomLeaves, phantomLeaves},
            {phantomLeaves, phantomLeaves, air, air, phantomLeaves},
            {air, phantomLeaves, air, air, phantomLeaves}
    };

    private IBlockState[][] variant2WeepingLeaves = new IBlockState[][]{
            {phantomLeaves, phantomLeaves, air, phantomLeaves, phantomLeaves},
            {phantomLeaves, phantomLeaves, phantomLeaves, phantomLeaves, phantomLeaves},
            {phantomLeaves, air, phantomLeaves, air, air},
            {phantomLeaves, air, phantomLeaves, air, air},
            {air, air, phantomLeaves, air, air},
            {air, air, air, air, air}
    };

    private IBlockState[][] variant3WeepingLeaves = new IBlockState[][]{
            {phantomLeaves, air, air, phantomLeaves, air},
            {phantomLeaves, phantomLeaves, air, phantomLeaves, phantomLeaves},
            {air, phantomLeaves, air, air, phantomLeaves},
            {air, phantomLeaves, air, air, phantomLeaves},
            {air, phantomLeaves, air, air, phantomLeaves},
            {air, phantomLeaves, air, air, phantomLeaves},
    };
    private IBlockState[][] variant4WeepingLeaves = new IBlockState[][]{
            {air, air, air, phantomLeaves, phantomLeaves},
            {air, air, phantomLeaves, phantomLeaves, phantomLeaves},
            {air, air, phantomLeaves, air, phantomLeaves},
            {air, air, phantomLeaves, air, phantomLeaves},
            {air, air, phantomLeaves, air, air},
            {air, air, phantomLeaves, air, air},
    };
}
