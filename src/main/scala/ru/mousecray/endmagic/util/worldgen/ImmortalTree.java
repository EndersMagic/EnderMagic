package ru.mousecray.endmagic.util.worldgen;

import net.minecraft.block.Block;
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

public class ImmortalTree
{
    public static void generate(World world, BlockPos pos, int length, int width, int countOfGroundRoots)
    {
        HashMap<BlockPos, IBlockState> toPlace = new HashMap<>();


        //generateBranchRecursive(toPlace, world.rand, pos.add(-10, 0, 1), length, width, VectorUtil.of(0, 1, 0));

        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width, VectorUtil.of(15 / 100f, -1, 15 / 100f), true);
        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width, VectorUtil.of(-15 / 100f, -1, 15 / 100f), true);
        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width, VectorUtil.of(15 / 100f, -1, -15 / 100f), true);
        generateRootRecursive(world, toPlace, pos.add(1, (int) (length * 0.6), -2), length, width, VectorUtil.of(-15 / 100f, -1, -15 / 100f), true);


        double angl = 180d / countOfGroundRoots;
        for (int i = 0; i < countOfGroundRoots; i++)
        {
            generateGroundRoot(world, toPlace, pos, length, width / (2 + (double) world.rand.nextInt(3)), VectorUtil.of(Math.cos(angl * i), -1, Math.sin(angl * i)));
        }

        for (Map.Entry<BlockPos, IBlockState> current : toPlace.entrySet())
            TaskManager.blocksToPlace.add(new Truple<>(world, current.getKey(), current.getValue()));
    }

    public static void generateBranchRecursive(Map<BlockPos, IBlockState> toPlace, Random rand, BlockPos pos, int length, double width, Vector3d dir)
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
                    VectorUtil.rotate(position, dir);
                    BlockPos pos2 = newPos.add(position.x, position.y, position.z);
                    if (toPlace.get(pos2) == null || toPlace.get(pos2).getBlock() == Blocks.LEAVES)
                        toPlace.put(pos2, Blocks.LOG.getDefaultState());
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

    public static void generateRootRecursive(World world, Map<BlockPos, IBlockState> toPlace, BlockPos pos, int length, double width, Vector3d dir, boolean isGrandRoot)
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
                    VectorUtil.rotate(position, dir);
                    BlockPos pos2 = newPos.add(position.x - Math.ceil(width) / 2, position.y - Math.ceil(width) / 2, position.z - Math.ceil(width) / 2);
                    toPlace.computeIfAbsent(pos2, k -> Blocks.LOG.getDefaultState());
                }
            }
            if (rand.nextInt(80) > 75d - 5d * (1d - i / length))
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
                            VectorUtil.rotate(position, dir);
                            BlockPos pos2 = newPos.add(position.x - Math.ceil(width) / 2, position.y - Math.ceil(width) / 2, position.z - Math.ceil(width) / 2);
                            toPlace.computeIfAbsent(pos2, k -> Blocks.LOG.getDefaultState());
                        }
                    }
                }
            }
            /*
             * Делаем другие корни. Не спрашивайте почему такой иф.
             */
            if (rand.nextInt(length) < length / Math.abs(30F - i / length * 20 - countOfRoots) + length * 0.5 / width && width >= 1.4 && ((i > 0.6 * length) ^ (length <= 35 * 0.5f)))
            {
                Vector3d newDir = VectorUtil.of(dir.x, dir.y, dir.z);
                VectorUtil.rotate(newDir, rand.nextInt(30) - 15, rand.nextInt(30) - 15, rand.nextInt(30) - 15);

                newDir.y = -Math.abs(newDir.y);

                countOfRoots++;
                generateRootRecursive(world, toPlace, newPos, (int) (length * 0.6F), width * 0.6, newDir, false);
            }
        }
    }

    public static void generateGroundRoot(World world, Map<BlockPos, IBlockState> toPlace, BlockPos posIn, int maxLength, double width, Vector3d dir)
    {
        Random rand = world.rand;
        maxLength = maxLength * 2;
        AdvancedBlockPos poss = new AdvancedBlockPos(posIn);

        for (double i = 0; i < maxLength; ++i)
        {
            width /= 1.01 + Math.sqrt(i) / 1000;
            poss.move(dir.x, 0, dir.z);
            int r = (int) Math.ceil(width / 2);

            for (int x = -r; x < r; x++)
            {
                for (int y = 0; y < r * 2; y++)
                {
                    for (int z = -r; z < r; z++)
                    {
                        toPlace.computeIfAbsent(poss.add(x, y, z), k -> Blocks.LOG.getDefaultState());
                    }
                }
            }

            /*
             * Если висим над воздухом, то идем вниз, ползая по стенам, пока не будет камень
             */
            int downHeight = 0;
            double oldDir = dir.y;
            while (i < maxLength && world.isAirBlock(poss.down()))
            {
                dir.y = -1;
                width /= 1.01 + Math.sqrt(i) / 1000;
                downHeight++;
                //dir.y = -downHeight / (double) maxLength;
                poss.move(EnumFacing.DOWN);
                i++;
                ArrayList<BlockPos> poses = new ArrayList<>();
                poses.add(poss.west());
                poses.add(poss.east());
                poses.add(poss.north());
                poses.add(poss.south());
                poses.add(poss.add(1, 0, 1));
                poses.add(poss.add(1, 0, -1));
                poses.add(poss.add(-1, 0, 1));
                poses.add(poss.add(-1, 0, -1));

                poses.removeIf(pos -> !world.isAirBlock(pos));

                if(poses.size() != 8 && poses.size() > 0)
                {
                    int random = Math.abs(rand.nextInt(poses.size()));
                    poss.setPos(poses.get(random));
                }

                for (int x = -r; x < r; x++)
                {
                    for (int y = 0; y < 2 * r; y++)
                    {
                        for (int z = -r; z < r; z++)
                        {
                            toPlace.computeIfAbsent(poss.add(x, y, z), k -> Blocks.LOG.getDefaultState());
                        }
                    }
                }
                if(downHeight > 15)
                    return;
            }
            dir.y = oldDir;

            if(world.rand.nextInt(maxLength) > maxLength * 0.8)
            {
                dir = VectorUtil.of(dir.x + rand.nextDouble() - rand.nextDouble(), dir.y + rand.nextDouble() - rand.nextDouble(), dir.z + rand.nextDouble() - rand.nextDouble());
                /*
                dir.x = Math.max(Math.min(dir.x, 1), -1);
                dir.y = Math.max(Math.min(dir.y, 1), -1);
                dir.z = Math.max(Math.min(dir.z, 1), -1);

                 */
            }
        }
    }

    private double inRange(double val, double min, double max)
    {
        return  Math.max(Math.min(val, max), min);
    }
}
