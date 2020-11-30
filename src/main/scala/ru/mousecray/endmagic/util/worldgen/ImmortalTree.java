package ru.mousecray.endmagic.util.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.TaskManager;
import ru.mousecray.endmagic.util.Truple;
import ru.mousecray.endmagic.util.VectorUtil;

import java.util.*;

public class ImmortalTree
{
    public static void generate(World world, BlockPos pos, int length, int width)
    {
        HashMap<BlockPos, IBlockState> toPlace = new HashMap<>();

        //generateBranchRecursive(toPlace, world.rand, pos.add(-10, 0, 1), length, width, VectorUtil.of(0, 1, 0));
        //generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width / 2.5D, VectorUtil.of(15 / 100f, -1, 15 / 100f));
        //generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width / 2.5D, VectorUtil.of(-15 / 100f, -1, 15 / 100f));
        //generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width / 2.5D, VectorUtil.of(15 / 100f, -1, -15 / 100f));
        //generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width / 2.5D, VectorUtil.of(-15 / 100f, -1, -15 / 100f));

        generateGroundRootRecursive(world, toPlace, new BlockPos.MutableBlockPos(pos), length, width / 2.5, VectorUtil.of(world.rand.nextInt(30) / 100d, 0, world.rand.nextInt(30) / 100d));

        for (Map.Entry<BlockPos, IBlockState> current : ImmutableList.copyOf(toPlace.entrySet()))
            TaskManager.blocksToPlace.add(new Truple<>(world, current.getKey(), current.getValue()));
    }

    public static void generateBranchRecursive(Map<BlockPos, IBlockState> toPlace, Random rand, BlockPos pos, int length, double width, Vector3d dir)
    {
        int countOfBranches = 0;
        for (double i = 0; i < length; ++i)
        {
            width /= 1.001;
            BlockPos newPos = pos.add(i * dir.x, i * dir.y, i * dir.z);
            for (int x = 0; x < Math.ceil(width); x++)
            {
                for (int z = 0; z < Math.ceil(width); z++)
                {
                    Vector3d position = VectorUtil.of(x, 0, z);
                    VectorUtil.rotate(position, dir);
                    BlockPos pos2 = newPos.add(position.x, position.y, position.z);
                    if (toPlace.get(pos2) == null || toPlace.get(pos2).getBlock() == Blocks.LEAVES)
                        toPlace.put(pos2, Blocks.LOG.getDefaultState());
                }

            }
            if (Math.ceil(width) <= 3)
            {
                for (int x = 0; x < Math.ceil(width) * 3; x++)
                {
                    for (int y = 0; y < Math.ceil(width) * 3; y++)
                    {
                        for (int z = 0; z < Math.ceil(width) * 3; z++)
                        {
                            BlockPos pos2 = newPos.add(x - Math.ceil(width), y - Math.ceil(width), z - Math.ceil(width));
                            toPlace.computeIfAbsent(pos2, k -> Blocks.LEAVES.getDefaultState());
                        }
                    }
                }
            }

            if (rand.nextInt(length) < length / Math.abs(30F - i / length * 20 - countOfBranches) + length * 0.5 / width && width >= 1 && ((i > 0.6 * length) ^ (length <= 35)))
            {
                Vector3d newDir = new Vector3d();
                newDir.x = dir.x + 0;
                newDir.y = dir.y + 0;
                newDir.z = dir.z + 0;
                VectorUtil.rotateX(newDir, rand.nextInt(60) - 30);
                VectorUtil.rotateY(newDir, rand.nextInt(60) - 30);
                VectorUtil.rotateZ(newDir, rand.nextInt(60) - 30);

                if (length >= 35) newDir.y = Math.abs(newDir.y);

                countOfBranches++;
                generateBranchRecursive(toPlace, rand, newPos, (int) (length * 0.8F), width * 0.7, newDir);
            }

            if (rand.nextInt(1000) == 1 && width >= 1 && length > 10 && length < 20)
            {
                for (int y = 0; y < 20; ++y)
                {
                    toPlace.put(newPos.add(0, -y, 0), Blocks.LOG.getDefaultState());
                }

                for (int x = 0; x < 3; x++)
                {
                    for (int y = 0; y < 3; y++)
                    {
                        for (int z = 0; z < 3; z++)
                        {
                            toPlace.put(newPos.add(x - 2 + 1, y - 20 + 1, z - 2 + 1), Blocks.GLOWSTONE.getDefaultState());
                        }
                    }
                }
            }
        }
    }


    public static void generateRootRecursive(World world, Map<BlockPos, IBlockState> toPlace, BlockPos pos, int length, double width, Vector3d dir)
    {
        boolean hasRoot = false;
        Random rand = world.rand;
        int countOfRoots = 0;
        for (double i = 0; i < length; ++i)
        {
            width /= 1.005;
            BlockPos newPos = pos.add(i * dir.x, i * dir.y, i * dir.z);
            for (int x = 0; x < Math.ceil(width); x++)
            {
                for (int z = 0; z < Math.ceil(width); z++)
                {
                    Vector3d position = VectorUtil.of(x, 0, z);
                    VectorUtil.rotate(position, dir);
                    BlockPos pos2 = newPos.add(position.x, position.y, position.z);
                    toPlace.computeIfAbsent(pos2, k -> Blocks.LOG.getDefaultState());
                    if (world.getBlockState(pos2).getBlock() != Blocks.AIR && !hasRoot)
                    {
                        generateGroundRootRecursive(world, toPlace, new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ()), 100, width * 0.8, VectorUtil.of(dir.x, 0, dir.z));
                        hasRoot = true;
                    }
                }
            }

            if (rand.nextInt(length) < length / Math.abs(30F - i / length * 20 - countOfRoots) + length * 0.5 / width && width >= 1.4 && ((i > 0.6 * length) ^ (length <= 35 * 0.5f)))
            {
                Vector3d newDir = VectorUtil.of(dir.x, dir.y, dir.z);
                VectorUtil.rotate(newDir, rand.nextInt(30) - 15, rand.nextInt(30) - 15, rand.nextInt(40) - 15);

                newDir.y = -Math.abs(newDir.y);

                countOfRoots++;
                generateRootRecursive(world, toPlace, newPos, (int) (length * 0.6F), width * 0.6, newDir);
            }
        }
    }

    public static void generateGroundRootRecursive(World world, Map<BlockPos, IBlockState> toPlace, BlockPos.MutableBlockPos pos, int maxLength, double width,  Vector3d dir)
    {
        for (double i = 0; i < maxLength; ++i)
        {
            BlockPos currentPos = pos.add(i * dir.x, i * dir.y, i * dir.z);
            toPlace.computeIfAbsent(currentPos, k -> Blocks.LOG.getDefaultState());
            while (i < maxLength && world.isAirBlock(currentPos.down()))
            {
                pos.move(EnumFacing.DOWN);
                currentPos = pos.add(i * dir.x, i * dir.y, i * dir.z);
                toPlace.computeIfAbsent(currentPos, k -> Blocks.LOG.getDefaultState());
                i++;
                if(world.isAirBlock(currentPos.west()))
                    pos.move(EnumFacing.WEST);
                else if(world.isAirBlock(currentPos.east()))
                    pos.move(EnumFacing.EAST);
                else if(world.isAirBlock(currentPos.north()))
                    pos.move(EnumFacing.NORTH);
                else if(world.isAirBlock(currentPos.south()))
                    pos.move(EnumFacing.SOUTH);
            }
        }
    }
}
