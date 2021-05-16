package ru.mousecray.endmagic.blocks.trees.dragon;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.blocks.trees.EMSapling;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.Arrays;

public class Sapling extends EMSapling {
    public Sapling() {
        super(EnderBlockTypes.EnderTreeType.DRAGON);
    }

    public boolean checkPlacement(World worldIn, BlockPos pos) {
        //TODO: add custom end grass and remove STONE from this
        //TODO: add custom end grass and remove STONE from this
        return Arrays.stream(EnumFacing.HORIZONTALS)
                .map(pos::offset)
                .map(worldIn::getBlockState)
                //TODO: add custom end grass and remove STONE from this
                .anyMatch(state -> EMUtils.isSoil(state, EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS));
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return Utils.isExistsDragonPowerSourceAround(world, pos);
    }
}
