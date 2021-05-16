package ru.mousecray.endmagic.blocks.trees.dragon;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.List;
import java.util.Random;

import static ru.mousecray.endmagic.blocks.trees.dragon.TreeState.TREE_STATE;

public class Leaves extends EMLeaves {
    public Leaves() {
        super(EnderBlockTypes.EnderTreeType.DRAGON, () -> EMBlocks.dragonSapling);
        setDefaultState(blockState.getBaseState().withProperty(TREE_STATE, TreeState.decay));
    }

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of(TREE_STATE);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return random.nextInt(state.getValue(TREE_STATE) == TreeState.fine ? 20 : 80) == 0 ? 1 : 0;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        Utils.updateTreeState(worldIn, pos, state, rand,
                newTreeState -> worldIn.setBlockState(pos, state.withProperty(TREE_STATE, newTreeState)));
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return !(entity instanceof EntityDragon) && super.canEntityDestroy(state, world, pos, entity);
    }
}
