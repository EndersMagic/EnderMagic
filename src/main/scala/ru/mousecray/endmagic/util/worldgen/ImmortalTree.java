package ru.mousecray.endmagic.util.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.VectorUtil;

public class ImmortalTree
{
    public static void generate(World world, BlockPos pos, int length, int width)
    {
        Vector3d vector3d = new Vector3d();
        vector3d.x = 0;
        vector3d.y = 1;
        vector3d.z = 0;
        generateBranchRecursive(world, pos, length, width, vector3d);
    }

    public static void generateBranchRecursive(World world, BlockPos pos, int length, double width, Vector3d dir)
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
                        if (world.isAirBlock(pos2) || world.getBlockState(pos2).getBlock() == Blocks.LEAVES)
                            world.setBlockState(pos2, Blocks.LOG.getDefaultState());
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
                            if (world.isAirBlock(pos2))
                                world.setBlockState(pos2, Blocks.LEAVES.getDefaultState());
                        }
                    }
                }
            }

            if (world.rand.nextInt(length) < length / Math.abs(30F - i / length * 20 - countOfBranches) + length * 0.5 / width && width >= 1 && ((i > 0.6 * length) ^ (length <= 35)))
            {
                Vector3d newDir = new Vector3d();
                newDir.x = dir.x + 0;
                newDir.y = dir.y + 0;
                newDir.z = dir.z + 0;
                VectorUtil.rotateX(newDir, world.rand.nextInt(60) - 30);
                VectorUtil.rotateY(newDir, world.rand.nextInt(60) - 30);
                VectorUtil.rotateZ(newDir, world.rand.nextInt(60) - 30);

                if (length >= 35)
                {
                    newDir.y = Math.abs(newDir.y);
                }
                countOfBranches++;
                generateBranchRecursive(world, newPos, (int) (length * 0.8F), width * 0.7, newDir);
            }

            if (world.rand.nextInt(1000) == 1 && width >= 1 && length > 10 && length < 20)
            {
                for (int y = 0; y < 10; ++y)
                {
                    world.setBlockState(newPos.add(0, -y, 0), Blocks.LOG.getDefaultState());
                }

                for (int x = 0; x < 3; x++)
                {
                    for (int y = 0; y < 3; y++)
                    {
                        for (int z = 0; z < 3; z++)
                        {
                            BlockPos pos2 = newPos.add(x - 2 - 1, y - 30 - 1, z - 2 - 1);
                            if (world.isAirBlock(pos2))
                                world.setBlockState(pos2, Blocks.GLOWSTONE.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
