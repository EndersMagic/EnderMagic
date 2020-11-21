package ru.mousecray.endmagic.util.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.TaskManager;
import ru.mousecray.endmagic.util.Truple;
import ru.mousecray.endmagic.util.VectorUtil;

import java.util.*;

public class ImmortalTree
{
    public static HashMap<BlockPos, IBlockState> toPlace = new HashMap<>();
    public static void generate(World world, BlockPos pos, int length, int width)
    {
        try
        {
            Vector3d vector3d = new Vector3d();
            vector3d.x = 0;
            vector3d.y = 1;
            vector3d.z = 0;
            generateBranchRecursive(world.rand, pos, length, width, vector3d);
            List<Map.Entry<BlockPos, IBlockState>> entries = ImmutableList.copyOf(toPlace.entrySet());
            for (Map.Entry<BlockPos, IBlockState> current: entries)
            {
                TaskManager.blocksToPlace.add(new Truple<>(world, current.getKey(), current.getValue()));
            }
            toPlace = new HashMap<>();
        }
        catch (Exception ignored)
        {

        }

    }

    public static void generateBranchRecursive(Random rand, BlockPos pos, int length, double width, Vector3d dir)
    {
        int countOfBranches = 0;
        for (double i = 0; i < length; ++i)
        {
            width /= 1.001;
            BlockPos newPos = pos.add(i * dir.x, i * dir.y, i * dir.z);
            for (int x = 0; x < Math.ceil(width); x++)
            {
                for (int y = 0; y < Math.ceil(width); y++)
                {
                    for (int z = 0; z < Math.ceil(width); z++)
                    {
                        BlockPos pos2 = newPos.add(x - Math.ceil(width) / 2, y - Math.ceil(width) / 2, z - Math.ceil(width) / 2);
                        if (toPlace.get(pos2) == null || toPlace.get(pos2).getBlock() == Blocks.LEAVES)
                            toPlace.put(pos2, Blocks.LOG.getDefaultState());
                    }
                }
            }
            if (Math.ceil(width) <= 3)
            {
                for (int x = 0; x < Math.ceil(width) * 2; x++)
                {
                    for (int y = 0; y < Math.ceil(width) * 2; y++)
                    {
                        for (int z = 0; z < Math.ceil(width) * 2; z++)
                        {
                            BlockPos pos2 = newPos.add(x - Math.ceil(width), y - Math.ceil(width), z - Math.ceil(width));
                            if (toPlace.get(pos2) == null)
                                toPlace.put(pos2, Blocks.LEAVES.getDefaultState());
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

                if (length >= 35)
                {
                    newDir.y = Math.abs(newDir.y);
                }
                countOfBranches++;
                generateBranchRecursive(rand, newPos, (int) (length * 0.8F), width * 0.7, newDir);
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
                            BlockPos pos2 = newPos.add(x - 2 + 1, y - 20 + 1, z - 2 + 1);
                            toPlace.put(pos2, Blocks.GLOWSTONE.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
