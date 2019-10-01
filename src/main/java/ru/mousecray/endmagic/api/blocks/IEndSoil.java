package ru.mousecray.endmagic.api.blocks;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEndSoil {
	default EndSoilType getSoilType() {
		return EndSoilType.STONE;
	}
	boolean onUseBonemeal(World world, BlockPos pos, Random rand, EntityPlayer player);
}