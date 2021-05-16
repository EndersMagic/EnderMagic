package ru.mousecray.endmagic.blocks.trees.dragon;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;
import ru.mousecray.endmagic.worldgen.trees.world.WorldGenDragonTreeWorld;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static ru.mousecray.endmagic.blocks.trees.dragon.TreeState.TREE_STATE;

public class Utils {
    static boolean isDragonPowerSource(World world, BlockPos p) {
        IBlockState blockState = world.getBlockState(p);
        Block block = blockState.getBlock();
        if (block == Blocks.DRAGON_EGG)
            return true;
        else if (block == Blocks.SKULL) {
            TileEntity tile = world.getTileEntity(p);
            if (tile instanceof TileEntitySkull)
                return ((TileEntitySkull) tile).getSkullType() == 5;
            else
                return false;
        } else
            return false;
    }

    static boolean isExistsDragonPowerSourceAround(World world, BlockPos pos) {
        if (world.provider instanceof WorldProviderEnd && WorldGenDragonTreeWorld.isInCentralIsland(pos.getX() >> 4, pos.getZ() >> 4))
            return !ReflectionHelper.<Boolean, DragonFightManager>getPrivateValue(DragonFightManager.class, ((WorldProviderEnd) world.provider).getDragonFightManager(), "dragonKilled");
        else {
            AtomicBoolean dragonPowerSourceExists = new AtomicBoolean(false);
            WorldGenUtils.generateInAreaBreakly(pos.add(-5, -5, -5), pos.add(5, 5, 5), p -> {
                if (isDragonPowerSource(world, p)) {
                    dragonPowerSourceExists.set(true);
                    return false;
                } else
                    return true;
            });
            return dragonPowerSourceExists.get();
        }
    }

    static void updateTreeState(World worldIn, BlockPos pos, IBlockState state, Random rand, Consumer<TreeState> onChangeState) {
        if (Utils.isExistsDragonPowerSourceAround(worldIn, pos)) {
            if (state.getValue(TREE_STATE) == TreeState.decay)
                onChangeState.accept(TreeState.fine);
        } else {
            if (state.getValue(TREE_STATE) == TreeState.fine)
                onChangeState.accept(TreeState.decay);
                worldIn.setBlockState(pos, state.withProperty(TREE_STATE, TreeState.decay));
        }
    }
}
