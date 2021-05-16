package ru.mousecray.endmagic.blocks.trees.dragon;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.trees.EMLog;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.worldgen.trees.WorldGenDragonTree;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static ru.mousecray.endmagic.blocks.trees.dragon.TreeState.TREE_STATE;

public class Log extends EMLog {

    public Log() {
        super(EnderBlockTypes.EnderTreeType.DRAGON);
        setTickRandomly(true);
    }

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of(LOG_AXIS, TREE_STATE);
    }

    public boolean canSustainSpecifiedLeaves(IBlockAccess world, IBlockState logState, BlockPos logPos, IBlockState leavesState, BlockPos leavesPos) {
        return logState.getValue(TREE_STATE) == TreeState.fine && super.canSustainSpecifiedLeaves(world, logState, logPos, leavesState, leavesPos);
    }

    @Override
    public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos) {
        return (state.getValue(TREE_STATE) == TreeState.fine ? 3 : 1) * super.getBlockHardness(state, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        IBlockState state = world.getBlockState(pos);
        return (state.getBlock() == this && state.getValue(TREE_STATE) == TreeState.fine ? 10 : 1) * super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return !(entity instanceof EntityDragon) && super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            Utils.updateTreeState(worldIn, pos, state, rand,
                    newTreeState -> setAllTrunk(worldIn, pos, newTreeState));

            if (state.getValue(TREE_STATE) == TreeState.decay) {
                /*
                if ((worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0)
                    worldIn.spawnEntity(new EntityFallingBlock(worldIn, (double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, worldIn.getBlockState(pos)));
                    */
                Blocks.SAND.updateTick(worldIn, pos, state, rand);
            }
        }
    }

    private void setAllTrunk(World worldIn, BlockPos pos, TreeState treeState) {
        BlockLog.EnumAxis axis = worldIn.getBlockState(pos).getValue(LOG_AXIS);
        EnumFacing direction = getTrunkDirection(axis);
        for (int i = -WorldGenDragonTree.maxTrunkLen; i <= WorldGenDragonTree.maxTrunkLen; i++) {
            BlockPos pos1 = pos.offset(direction, i);
            IBlockState blockState = worldIn.getBlockState(pos1);
            if (blockState.getBlock() == this)
                worldIn.setBlockState(pos1, blockState.withProperty(TREE_STATE, treeState));
        }
    }

    private EnumFacing getTrunkDirection(BlockLog.EnumAxis axis) {
        switch (axis) {
            case X:
                return EnumFacing.EAST;
            case Y:
                return EnumFacing.UP;
            case Z:
                return EnumFacing.NORTH;
            default:
                return EnumFacing.NORTH;
        }
    }
}
