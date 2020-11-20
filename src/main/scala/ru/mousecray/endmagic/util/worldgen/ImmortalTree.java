package ru.mousecray.endmagic.util.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.VectorUtil;

public class ImmortalTree
{
    public static void generate(World world, BlockPos pos, BlockPos pos2, int length)
    {
        double l = length;
        Vector3d vector3d = new Vector3d();
        vector3d.x = pos2.getX() / l;
        vector3d.y = pos2.getY() / l;
        vector3d.z = pos2.getZ() / l;
        generateBranchRecursive(world, pos, length, vector3d, true, Blocks.LOG.getDefaultState());
    }

    public static void generateBranchRecursive(World world, BlockPos pos, int length, Vector3d dir, boolean isObtuse, IBlockState state)
    {
        for (double i = 0; i < length; ++i)
        {
            BlockPos newPos = pos.add(i * dir.x, i * dir.y, i * dir.z);
            if(world.rand.nextInt(length) > length / 6 && length > 4)
            {
                Vector3d newDir = dir;
                VectorUtil.rotateX(newDir, world.rand.nextInt(360));
                VectorUtil.rotateY(newDir, world.rand.nextInt(360));
                VectorUtil.rotateZ(newDir, world.rand.nextInt(360));
                generateBranchRecursive(world, newPos, (int) (length / 1.5), newDir, false, state);
            }
            else
            {
                world.setBlockState(newPos, state);
            }
        }
    }


}
