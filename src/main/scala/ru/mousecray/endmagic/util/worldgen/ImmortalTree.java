package ru.mousecray.endmagic.util.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.VectorUtil;

public class ImmortalTree
{
    public static void generate(World world, BlockPos pos, BlockPos pos2, int length, int width)
    {
        double l = length;
        Vector3d vector3d = new Vector3d();
        vector3d.x = pos2.getX() / l;
        vector3d.y = pos2.getY() / l;
        vector3d.z = pos2.getZ() / l;
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
            if(Math.ceil(width) <= 5)
            {
                for (int x = 0; x < Math.ceil(width) * 1.5; x++)
                {
                    for (int y = 0; y < Math.ceil(width) * 1.5; y++)
                    {
                        for (int z = 0; z < Math.ceil(width) * 1.5; z++)
                        {
                            BlockPos pos2 = newPos.add(x - Math.ceil(width) * 1.5 / 2, y - Math.ceil(width) * 1.5 / 2, z - Math.ceil(width) * 1.5 / 2);
                            if (world.isAirBlock(pos2))
                                world.setBlockState(pos2, Blocks.LEAVES.getDefaultState());
                        }
                    }
                }
            }

            if (world.rand.nextInt(length) < length / Math.abs(30F - i / length * 20 - countOfBranches) && width >= 0.4 && ((i > 0.6 * length) ^ (length <= 35)))
            {
                Vector3d newDir = new Vector3d();
                newDir.x = dir.x + 0;
                newDir.y = dir.y + 0;
                newDir.z = dir.z + 0;
                VectorUtil.rotateX(newDir, world.rand.nextInt(60) - 30);
                VectorUtil.rotateY(newDir, world.rand.nextInt(60) - 30);
                VectorUtil.rotateZ(newDir, world.rand.nextInt(60) - 30);

                if(length >= 35)
                {
                    newDir.y = Math.abs(newDir.y);
                    newDir.y *= (1.2 - i / length) % 1;
                }
                countOfBranches++;
                generateBranchRecursive(world, newPos, (int) (length * 0.8F), width * 0.7, newDir);
            }

        }
    }
}
