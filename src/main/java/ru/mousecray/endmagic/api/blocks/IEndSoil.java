package ru.mousecray.endmagic.api.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import javax.annotation.Nonnull;
import java.util.Random;

public interface IEndSoil {
    default EndSoilType getSoilType() {
        return EndSoilType.STONE;
    }

    boolean canUseBonemeal();

    /**
     * WARN: This method is not return chance to grow, it only return plant!
     */
    @Nonnull
    default IBlockState getBonemealCrops(Random rand, EntityPlayer player, IBlockState soil) {
        return Blocks.TALLGRASS.getDefaultState();
    }
}