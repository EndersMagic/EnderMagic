package ru.mousecray.endmagic.api.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.Random;

/**
 * This interface contains the basic methods for working with endstone soils. All endstone soils must implement it!
 */
@FunctionalInterface
public interface IEndSoil {

    /**
     * Return type of this endstone
     */
    default EndSoilType getSoilType() {
        return EndSoilType.STONE;
    }

    /**
     * Return boolean value of bonemeal can used on this block
     */
    boolean canUseBonemeal();

    /**
     * Call to WorldServer for set plant with chance.
     */
    default void growPlant(World world, BlockPos soilPos, IBlockState soilState, Random rand) {
        BlockPos plantPos = soilPos.up();
        if (!world.isRemote && world.isAirBlock(plantPos) && rand.nextInt(10) > 7)
            world.setBlockState(plantPos, EMBlocks.enderTallgrass.getDefaultState());
    }
}