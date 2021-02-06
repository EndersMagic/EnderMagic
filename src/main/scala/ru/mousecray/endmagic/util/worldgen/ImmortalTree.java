package ru.mousecray.endmagic.util.worldgen;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.AdvancedBlockPos;
import ru.mousecray.endmagic.util.TaskManager;
import ru.mousecray.endmagic.util.Truple;
import ru.mousecray.endmagic.util.VectorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.minecraft.block.BlockLog.LOG_AXIS;

public class ImmortalTree
{
    public static void generate(World world, BlockPos pos, int length, double width, int countOfGroundRoots)
    {
        HashMap<BlockPos, IBlockState> toPlace = new HashMap<>();
        Random rand = world.rand;
        int r = (int) Math.ceil(width / 8);

        double angl = 180d / countOfGroundRoots * 4;
        int realCountOfRoots = (countOfGroundRoots + 4 - 1) / 4;

        for (int i = 0; i < realCountOfRoots; i++)
            generateGroundRoot(world, toPlace, pos, length, width / (1 + (double) rand.nextInt(3)), VectorUtil.of(Math.cos(angl * i), 0, Math.sin(angl * i)));

        for (int i = realCountOfRoots; i < realCountOfRoots * 2; i++)
            generateGroundRoot(world, toPlace, pos, length, width / (1 + (double) rand.nextInt(3)), VectorUtil.of(Math.cos(angl * i), 0, Math.sin(angl * i)));

        for (int i = realCountOfRoots * 2; i < realCountOfRoots * 3; i++)
            generateGroundRoot(world, toPlace, pos, length, width / (1 + (double) rand.nextInt(3)), VectorUtil.of(Math.cos(angl * i), 0, Math.sin(angl * i)));

        for (int i = realCountOfRoots * 3; i < realCountOfRoots * 4; i++)
            generateGroundRoot(world, toPlace, pos, length, width / (1 + (double) rand.nextInt(3)), VectorUtil.of(Math.cos(angl * i), 0, Math.sin(angl * i)));

        /*
        int r = (int) width;
        for (int y = 0; y < length; y++)
        {
            width /= 1.001;
            for (int x = -r; x < r; x++)
            {
                for (int z = -r; z < r; z++)
                {
                    if(x * x + z * z - width * width < 2)
                    {
                        BlockPos pos2 = pos.add(x, y, z);
                        toPlace.put(pos2, Blocks.LOG.getDefaultState());
                        if(y == length - 1 && rand.nextInt(5) > 3)
                        {
                            generateRelief(world, toPlace, pos2, VectorUtil.of(x / (r * 10.0), -1, z / (r * 10.0)), length * 2, width / (3 + rand.nextInt(2)));
                            if (rand.nextInt(10) > 6)
                            {
                                //generateBranchRecursive(toPlace, rand, pos2, length, width / 2, VectorUtil.of(x / (r * 2.0), 1, z / (r * 2.0)), true);
                            }
                        }
                    }
                }
            }
        }

        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width * 1.1, VectorUtil.of(15 / 100f, -1, 15 / 100f), true);
        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width * 1.1, VectorUtil.of(-15 / 100f, -1, 15 / 100f), true);
        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width * 1.1, VectorUtil.of(15 / 100f, -1, -15 / 100f), true);
        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width * 1.1, VectorUtil.of(-15 / 100f, -1, -15 / 100f), true);
        //*/

        for (Map.Entry<BlockPos, IBlockState> current : toPlace.entrySet())
            TaskManager.blocksToPlace.add(new Truple<>(world, current.getKey(), current.getValue()));
    }

    private static void generateBranchRecursive(Map<BlockPos, IBlockState> toPlace, Random rand, BlockPos pos, int length, double width, Vector3d dir)
    {
        int countOfBranches = 0;
        for (double i = 0; i < length; ++i)
        {
            width /= 1.001; // каждый новых блок немного сужаем ширину ветки. Выглядит маленько, но этого более чем хватает.
            /*
             * Это поворот плоскасти, установка блоков ствола.
             */
            BlockPos newPos = pos.add(i * dir.x, i * dir.y, i * dir.z);
            for (int x = 0; x < Math.ceil(width); x++)
            {
                for (int z = 0; z < Math.ceil(width); z++)
                {
                    Vector3d position = VectorUtil.of(x, 0, z);
                    VectorUtil.rotateFromDirs(position, dir);
                    BlockPos pos2 = newPos.add(position.x, position.y, position.z);
                    IBlockState state = toPlace.get(pos2);
                    if (state == null || state.getBlock() == Blocks.LEAVES)
                        toPlace.put(pos2, getLogWithDir(dir));
                }
            }

            /*
             * Это установка блоков листвы если ветка узкая.
             */
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

            /*
             * Создание дочерних веток
             */
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

            /*
             * Создание плодов
             */
            if (rand.nextInt(1000) == 1 && width >= 1 && length > 10 && length < 20)
            {
                for (int y = 0; y < 20; ++y)
                {
                    toPlace.put(newPos.add(0, -y, 0),getLogWithDir(dir));
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

    private static void generateBranchRecursive(Map<BlockPos, IBlockState> toPlace, Random rand, BlockPos startPosition, int length, double width, Vector3d dir, boolean isGroundBranch)
    {
        int countOfBranches = 0;
        AdvancedBlockPos pos = new AdvancedBlockPos(startPosition);
        for (int y = 0; y < length; y++)
        {
            width /= 1.001;
            if(isGroundBranch)
                pos.move(dir.x * (1 + Math.pow(y, 0.5)), dir.y, dir.z * (1 + Math.pow(y, 0.5)));
            else
                pos.move(dir);

            for (int x = 0; x < Math.ceil(width); x++)
            {
                for (int z = 0; z < Math.ceil(width); z++)
                {
                    Vector3d position = VectorUtil.of(x, 0, z);
                    VectorUtil.rotate(position, dir);
                    BlockPos pos2 = pos.add(position.x, position.y, position.z);
                    if (toPlace.get(pos2) == null || toPlace.get(pos2).getBlock() == Blocks.LEAVES)
                        toPlace.put(pos2, getLogWithDir(dir));
                }
            }

            if (Math.ceil(width) <= 3)
            {
                for (int x = 0; x < Math.ceil(width) * 3; x++)
                {
                    for (int j = 0; j < Math.ceil(width) * 3; j++)
                    {
                        for (int z = 0; z < Math.ceil(width) * 3; z++)
                        {
                            BlockPos pos2 = pos.add(x - Math.ceil(width), j - Math.ceil(width), z - Math.ceil(width));
                            toPlace.computeIfAbsent(pos2, k -> Blocks.LEAVES.getDefaultState());
                        }
                    }
                }
            }

            if(rand.nextInt(10) > 7 && countOfBranches < 8 && width > 4)
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
                generateBranchRecursive(toPlace, rand, pos, (int) (length * 0.8F), width * 0.7, newDir, false);
            }
        }
    }

    private static void generateRootRecursive(World world, Map<BlockPos, IBlockState> toPlace, BlockPos pos, int length, double width, Vector3d dir, boolean isGrandRoot)
    {
        Random rand = world.rand;
        int countOfRoots = 0;
        AdvancedBlockPos newPos = new AdvancedBlockPos(pos);
        for (double i = 0; i < length; ++i)
        {
            width /= 1.005;

            newPos.move(dir);
            /*
             * Это поворот плоскасти, установка блоков и создание ползущих корней.
             */
            for (int x = 0; x < Math.ceil(width); x++)
            {
                for (int z = 0; z < Math.ceil(width); z++)
                {

                    Vector3d position = VectorUtil.of(x, 0, z);
                    VectorUtil.rotateFromDirs(position, dir);
                    BlockPos pos2 = newPos.add(position.x - Math.ceil(width) / 2, position.y - Math.ceil(width) / 2, position.z - Math.ceil(width) / 2);
                    toPlace.computeIfAbsent(pos2, k -> getLogWithDir(dir));
                }
            }
            if (!(rand.nextInt(80) > 75d - 5d * (1d - i / length)))
            {
                generateGroundRoot(world, toPlace, new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ()), 60, width,
                        VectorUtil.of(dir.x + rand.nextInt(50) / 100d - rand.nextInt(50) / 100d,  dir.y / 2, dir.z + rand.nextInt(50) / 100d - rand.nextInt(50) / 100d));
            }
            /*
             * Если висим над воздухом, то идем вниз, ползая по стенам, пока не будет камень. Но все это только для не "главных" корней
             */
            if(!isGrandRoot)
            {
                int downHeight = 0;
                while (i < length && world.isAirBlock(newPos.down()))
                {
                    downHeight++;
                    if (downHeight > 15)
                        return;
                    //dir.y = -downHeight / (double) maxLength;
                    newPos.move(EnumFacing.DOWN);
                    i++;
                    ArrayList<BlockPos> poses = new ArrayList<>();
                    poses.add(newPos.west());
                    poses.add(newPos.east());
                    poses.add(newPos.north());
                    poses.add(newPos.south());
                    poses.add(newPos.add(1, 0, 1));
                    poses.add(newPos.add(1, 0, -1));
                    poses.add(newPos.add(-1, 0, 1));
                    poses.add(newPos.add(-1, 0, -1));

                    poses.removeIf(pos2 -> !world.isAirBlock(pos2));

                    if (poses.size() != 8)
                    {
                        newPos.setPos(poses.get(world.rand.nextInt(poses.size())));
                    }

                    /*
                     * Это вращение плоскасти и установка блоков.
                     */
                    for (int x = 0; x < Math.ceil(width); x++)
                    {
                        for (int z = 0; z < Math.ceil(width); z++)
                        {
                            Vector3d position = VectorUtil.of(x, 0, z);
                            VectorUtil.rotateFromDirs(position, dir);
                            BlockPos pos2 = newPos.add(position.x - Math.ceil(width) / 2, position.y - Math.ceil(width) / 2, position.z - Math.ceil(width) / 2);
                            toPlace.computeIfAbsent(pos2, k -> getLogWithDir(dir));
                        }
                    }
                }
            }
            /*
             * Делаем другие корни. Не спрашивайте почему такой иф.
             */
            /*
            if (rand.nextInt(length) < length / Math.abs(30F - i / length * 20 - countOfRoots) + length * 0.5 / width && width >= 1.4 && ((i > 0.6 * length) ^ (length <= 35 * 0.5f)) && !isGrandRoot || i > length * 0.6)
            {
                Vector3d newDir = VectorUtil.of(dir.x, dir.y, dir.z);
                VectorUtil.rotate(newDir, rand.nextInt(30) - 15, rand.nextInt(30) - 15, rand.nextInt(30) - 15);

                newDir.y = -Math.abs(newDir.y);

                countOfRoots++;
                generateRootRecursive(world, toPlace, newPos, (int) (length * 0.6F), width * 0.6, newDir, false);
            }
             */
        }
    }

    private static void generateRelief(World world, Map<BlockPos, IBlockState> toPlace, BlockPos startPos,  Vector3d dir, int maxLength, double width)
    {
        Random rand = world.rand;
        AdvancedBlockPos pos = new AdvancedBlockPos(startPos);
        for (int y = 0; y < maxLength; y++)
        {
            if (!world.isAirBlock(pos))
            {
                generateGroundRoot(world,toPlace, pos, maxLength - y, width, dir);
                return;
            }
            pos.move(dir.x * (1 + Math.pow(y, 1.7) / (double) maxLength), dir.y, dir.z * (1 + Math.pow(y, 1.7) / (double) maxLength));
            int r = (int) width;

            for (int x = -r; x < r; x++)
            {
                for (int z = -r; z < r; z++)
                {
                    if(x * x + z * z - width * width < 1.5 + rand.nextInt(2))
                    {
                        toPlace.put(pos.add(x, 0, z), getLogWithDir(dir));
                    }
                }
            }
        }
    }

    private static void generateGroundRoot(World world, Map<BlockPos, IBlockState> toPlace, BlockPos posIn, int maxLength, double width, Vector3d dir)
    {
        Vector3d dirDown = VectorUtil.of(0, -1, 0);
        Random rand = world.rand;
        maxLength = maxLength * 2;
        AdvancedBlockPos poss = new AdvancedBlockPos(posIn);
        for (double i = 0; i < maxLength; ++i)
        {
            dir.y = 0;
            width /= 1.01 + Math.sqrt(i) / 1000;
            poss.move(dir.x, dir.y, dir.z);
            int r = (int) Math.ceil(width / 2);

            /*
             * Если висим над воздухом, то идем вниз, ползая по стенам, пока не будет камень
             */
            int downHeight = 0;
            while (i < maxLength && needGoDown(world.getBlockState(poss.down())))
            {
                i++;
                width /= 1.01 + Math.sqrt(i) / 1000;
                downHeight++;
                poss.move(EnumFacing.DOWN);

                ArrayList<BlockPos> poses = new ArrayList<>();
                poses.add(poss.west());
                poses.add(poss.east());
                poses.add(poss.north());
                poses.add(poss.south());
                poses.add(poss.add(1, 0, 1));
                poses.add(poss.add(1, 0, -1));
                poses.add(poss.add(-1, 0, 1));
                poses.add(poss.add(-1, 0, -1));
                int oldSize = poses.size();
                poses.removeIf(pos -> !world.isAirBlock(pos));

                if(poses.size() != oldSize && poses.size() > 0)
                {
                    int random = Math.abs(rand.nextInt(poses.size()));
                    poss.setPos(poses.get(random));
                }
                r = (int) Math.ceil(width / 2);
                for (int x = -r; x <= r; x++)
                {
                    for (int z = -r; z <= r; z++)
                    {
                        if (x * x + z * z - r * r < 0.4)
                        {
                            Vector3d position = VectorUtil.of(x, 0, z);
                            VectorUtil.rotateFromDirs(position, dirDown);
                            BlockPos pos2 = poss.add(position.x, position.y, position.z);
                            toPlace.put(pos2, getLogWithDir(dirDown));
                        }
                    }
                }
                if(downHeight > 15)
                    return;
            }

            for (int x = -r; x <= r; x++)
            {
                for (int z = -r; z <= r; z++)
                {
                    if (x * x + z * z - r * r < 0.4)
                    {
                        Vector3d position = VectorUtil.of(x, 0, z);
                        VectorUtil.rotateFromDirs(position, dir);
                        toPlace.put(poss.add(position.x, position.y, position.z), getLogWithDir(dir));
                    }
                }
            }

            if(world.rand.nextInt(maxLength) > maxLength * 0.8)
            {
                dir = VectorUtil.of(dir.x + rand.nextDouble() - rand.nextDouble(), 0, dir.z + rand.nextDouble() - rand.nextDouble());

                dir.x = inNormalRange(dir.x);
                dir.z = inNormalRange(dir.z);
            }
        }
    }

    private static boolean needGoDown(IBlockState state)
    {
        return state.getBlock() == Blocks.LOG || state.getBlock() == Blocks.AIR || state.getBlock() == Blocks.LEAVES;
    }

    private static IBlockState getLogWithDir(Vector3d dir)
    {
        double x = Math.abs(dir.x), y = Math.abs(dir.y), z = Math.abs(dir.z);
        if(x > y)
        {
            if (x > z)
                return Blocks.LOG.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
            else
                return Blocks.LOG.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
        }
        else if(y > x)
        {
            if(y > z)
                return Blocks.LOG.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
            else
                return Blocks.LOG.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
        }
        else
        {
            return Blocks.LOG.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
        }
    }

    private static double inNormalRange(double val)
    {
        return Math.max(Math.min(val, 1), -1);
    }
}
