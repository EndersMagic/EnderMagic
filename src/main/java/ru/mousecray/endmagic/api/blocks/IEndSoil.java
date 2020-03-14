package ru.mousecray.endmagic.api.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import java.util.Random;

public interface IEndSoil {
    default EndSoilType getSoilType() {
        return EndSoilType.STONE;
    }

    boolean canUseBonemeal();

    default IBlockState getBonemealCrops(Random rand, EntityPlayer player, IBlockState soil) {
        return Blocks.TALLGRASS.getDefaultState();
    }
}