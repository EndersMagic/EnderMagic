package ru.mousecray.endmagic.worldgen.trees;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.tileentity.TilePhantomAvoidingBlockBase;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.Vec2i;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.*;
import java.util.function.Consumer;

public class WorldGenPhantomTree extends WorldGenEnderTree {

    public static final Vec3i areaRequirementsMin = new Vec3i(-3, 0, -3);
    public static final Vec3i areaRequirementsMax = new Vec3i(3, 9, 3);
    private final BlockPos[] airPositions1 = new BlockPos[]{
            new BlockPos(-2, 7, -1),
            new BlockPos(-2, 7, 0),
            new BlockPos(-2, 7, 1)
    };
    private final BlockPos[] airPositions2 = new BlockPos[]{
            new BlockPos(2, 7, -1),
            new BlockPos(2, 7, 0),
            new BlockPos(2, 7, 1)
    };
    private final BlockPos[] airPositions3 = new BlockPos[]{
            new BlockPos(-1, 7, 2),
            new BlockPos(0, 7, 2),
            new BlockPos(1, 7, 2)
    };
    private final BlockPos[] airPositions4 = new BlockPos[]{
            new BlockPos(-1, 7, -2),
            new BlockPos(0, 7, -2),
            new BlockPos(1, 7, -2)
    };
    private final BlockPos[] airPositions5 = new BlockPos[]{
            new BlockPos(0, 8, -1),
            new BlockPos(1, 8, -1),
            new BlockPos(1, 8, 0),
            new BlockPos(1, 8, 1),
            new BlockPos(0, 8, 1),
            new BlockPos(-1, 8, 1),
            new BlockPos(-1, 8, 0),
            new BlockPos(-1, 8, -1),
    };

    public WorldGenPhantomTree(boolean notify) {
        super(notify, areaRequirementsMin, areaRequirementsMax);
    }

    private IBlockState phantomLog = EMBlocks.enderLog.stateWithBlockType(EnderBlockTypes.EnderTreeType.PHANTOM);
    private IBlockState phantomLeaves = EMBlocks.enderLeaves.stateWithBlockType(EnderBlockTypes.EnderTreeType.PHANTOM);
    private IBlockState air = Blocks.AIR.getDefaultState();

    @SuppressWarnings("unused")
	private void setWithOffset(World world, BlockPos position, Vec3i offset, IBlockState state) {
        world.setBlockState(position.add(offset), state);
        ((TilePhantomAvoidingBlockBase) world.getTileEntity(position)).offsetFromSapling = offset;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (canGenerateThereAvaiable(worldIn, position)) {
            WorldSetWithTile specialWorld = new WorldSetWithTile(worldIn, position);

            for (int y = 0; y < 5; y++)
                specialWorld.setBlockState(position.add(0, y, 0), phantomLog);

            //worldIn.setTileEntity(position, new TilePhantomAvoidingBlockMaster());
            Consumer<BlockPos> setLeavesAt = pos -> specialWorld.setBlockState(pos, phantomLeaves);
            WorldGenUtils.generateInArea(position.add(-2, 5, -2), position.add(2, 6, 2), setLeavesAt);
            WorldGenUtils.generateInArea(position.add(-2, 7, -2), position.add(2, 7, 2), setLeavesAt);
            WorldGenUtils.generateInArea(position.add(-1, 8, -1), position.add(1, 8, 1), setLeavesAt);

            aireLeaves(worldIn, rand, position, airPositions1, 0.8);
            aireLeaves(worldIn, rand, position, airPositions2, 0.8);
            aireLeaves(worldIn, rand, position, airPositions3, 0.8);
            aireLeaves(worldIn, rand, position, airPositions4, 0.8);
            aireLeaves(worldIn, rand, position, airPositions5, 0.9);

            ArrayList<IBlockState[][][]> list = Lists.newArrayList(variant1WeepingLeaves, variant2WeepingLeaves, variant3WeepingLeaves, variant4WeepingLeaves);
            Collections.shuffle(list);

            //idea: генерировать вертикальные полосы листвы в точках

            boolean inverted = rand.nextBoolean();
            generatePlaneWithAxis(specialWorld, position.add(3, 6, inverted ? -2 : 2), new Vec2i(0, inverted ? 1 : -1), list.get(0)[0]);
            generatePlaneWithAxis(specialWorld, position.add(2, 4, inverted ? -2 : 2), new Vec2i(0, inverted ? 1 : -1), list.get(0)[1]);

            inverted = rand.nextBoolean();
            generatePlaneWithAxis(specialWorld, position.add(inverted ? -2 : 2, 6, 3), new Vec2i(inverted ? 1 : -1, 0), list.get(1)[0]);
            generatePlaneWithAxis(specialWorld, position.add(inverted ? -2 : 2, 4, 2), new Vec2i(inverted ? 1 : -1, 0), list.get(1)[1]);

            inverted = rand.nextBoolean();
            generatePlaneWithAxis(specialWorld, position.add(-3, 6, inverted ? -2 : 2), new Vec2i(0, inverted ? 1 : -1), list.get(2)[0]);
            generatePlaneWithAxis(specialWorld, position.add(-2, 4, inverted ? -2 : 2), new Vec2i(0, inverted ? 1 : -1), list.get(2)[1]);

            inverted = rand.nextBoolean();
            generatePlaneWithAxis(specialWorld, position.add(inverted ? -2 : 2, 6, -3), new Vec2i(inverted ? 1 : -1, 0), list.get(3)[0]);
            generatePlaneWithAxis(specialWorld, position.add(inverted ? -2 : 2, 4, -2), new Vec2i(inverted ? 1 : -1, 0), list.get(3)[1]);

            List<Pair<Vec3i, Set<Vec3i>>> centerLeaves = ImmutableList.of(
                    Pair.of(new Vec3i(1, 4, 0), ImmutableSet.of(new Vec3i(1, 4, 1), new Vec3i(1, 4, -1))),
                    Pair.of(new Vec3i(0, 4, 1), ImmutableSet.of(new Vec3i(1, 4, 1), new Vec3i(-1, 4, 1))),
                    Pair.of(new Vec3i(-1, 4, 0), ImmutableSet.of(new Vec3i(-1, 4, 1), new Vec3i(-1, 4, -1))),
                    Pair.of(new Vec3i(0, 4, -1), ImmutableSet.of(new Vec3i(-1, 4, -1), new Vec3i(1, 4, -1)))
            );
            Pair<Vec3i, Set<Vec3i>> centerChosenPos = centerLeaves.get(list.indexOf(rand.nextBoolean()?variant4WeepingLeaves:variant2WeepingLeaves));
            specialWorld.setBlockState(position.add(centerChosenPos.getLeft()), phantomLeaves);

            List<Vec3i> middleLeaves = Lists.newArrayList(
                    new Vec3i(-1, 4, -1),
                    new Vec3i(1, 4, -1),
                    new Vec3i(1, 4, 1),
                    new Vec3i(-1, 4, 1)
            );
            middleLeaves.removeAll(centerChosenPos.getRight());
            BlockPos middleChosenPos = position.add(middleLeaves.get(rand.nextInt(middleLeaves.size())));

            specialWorld.setBlockState(middleChosenPos, phantomLeaves);
            specialWorld.setBlockState(middleChosenPos.down(), phantomLeaves);

            return true;
        } else
            return false;
    }

    private void aireLeaves(World worldIn, Random rand, BlockPos position, BlockPos[] airPositions, double chance) {
        boolean prevAired = false;
        for (BlockPos pos : airPositions)
            if (!prevAired && rand.nextFloat() > chance) {
                worldIn.setBlockState(position.add(pos), air);
                prevAired = true;
            } else
                prevAired = false;
    }

    private void generatePlaneWithAxis(WorldSetWithTile world, BlockPos start, Vec2i vec2i, IBlockState[][] iBlockStates) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int y = 0; y < iBlockStates.length; y++) {
            for (int horizontal = 0; horizontal < iBlockStates[y].length; horizontal++) {
                pos.setPos(start.getX() + vec2i.x * horizontal, start.getY() - y, start.getZ() + vec2i.y * horizontal);
                world.setBlockState(pos, iBlockStates[y][horizontal]);
            }
        }
    }

    //@formatter:off
    private IBlockState[][][] variant1WeepingLeaves = new IBlockState[][][]{{
            {phantomLeaves, air,                phantomLeaves,  phantomLeaves,  air},
            {phantomLeaves, phantomLeaves,      air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,      air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,      air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,      air,            air,            phantomLeaves},
            {air,           phantomLeaves,      air,            air,            phantomLeaves}
    },{
            {air, air,      phantomLeaves,  air,  air},
            {air, air,      phantomLeaves,            air,  air},
            {air, air,      phantomLeaves,            air,  air},
            {air, air,      air,            air,  air}
    }};

    private IBlockState[][][] variant2WeepingLeaves = new IBlockState[][][]{{
            {phantomLeaves, phantomLeaves,  air,            phantomLeaves,  phantomLeaves},
            {phantomLeaves, phantomLeaves,  phantomLeaves,  phantomLeaves,  phantomLeaves},
            {phantomLeaves, air,            phantomLeaves,  air,            air},
            {phantomLeaves, air,            phantomLeaves,  air,            air},
            {air,           air,            phantomLeaves,  air,            air},
            {air,           air,            air,            air,            air}
    },{
            {air, air,  air,  phantomLeaves,  air},
            {air, air,  air,  phantomLeaves,  air},
            {air, air,  air,  air,            air},
            {air, air,  air,  air,            air}
    }};

    private IBlockState[][][] variant3WeepingLeaves = new IBlockState[][][]{{
            {phantomLeaves, air,            air, phantomLeaves, air},
            {phantomLeaves, phantomLeaves,  air, phantomLeaves, phantomLeaves},
            {air,           phantomLeaves,  air, air,           phantomLeaves},
            {air,           phantomLeaves,  air, air,           phantomLeaves},
            {air,           phantomLeaves,  air, air,           phantomLeaves},
            {air,           phantomLeaves,  air, air,           phantomLeaves},
    },{
            {air, air,  phantomLeaves, air, air},
            {air, air,  phantomLeaves, air, air},
            {air, air,  air,           air, air},
            {air, air,  air,           air, air}
    }};

    private IBlockState[][][] variant4WeepingLeaves = new IBlockState[][][]{{
            {air, air, air,             phantomLeaves,  phantomLeaves},
            {air, air, phantomLeaves,   phantomLeaves,  phantomLeaves},
            {air, air, phantomLeaves,   air,            phantomLeaves},
            {air, air, phantomLeaves,   air,            phantomLeaves},
            {air, air, phantomLeaves,   air,            air},
            {air, air, phantomLeaves,   air,            air},
    },{
            {air, phantomLeaves,    air,   air,  air},
            {air, phantomLeaves,    air,   air,  air},
            {air, air,              air,   air,  air},
            {air, air,              air,   air,  air}
    }};
    //@formatter:on

    private class WorldSetWithTile {
        private final World world;
        private final BlockPos saplingPosition;

        private WorldSetWithTile(World world, BlockPos saplingPosition) {
            this.world = world;
            this.saplingPosition = saplingPosition;
        }

        public boolean setBlockState(BlockPos pos, IBlockState state) {
            boolean r = world.setBlockState(pos, state);
            if (r && (state == phantomLog || state == phantomLeaves))
                ((TilePhantomAvoidingBlockBase) world.getTileEntity(pos)).offsetFromSapling = pos.subtract(saplingPosition);
            return r;
        }
    }
}
