package ru.mousecray.endmagic.world.gen.ores;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.world.gen.WorldGenMinableEnd;
import ru.mousecray.endmagic.init.EMBlocks;

public class WorldGenEnderOre extends WorldGenMinableEnd {

	public WorldGenEnderOre() {
		super(EMBlocks.enderOre.getDefaultState(), 3);
	}
	
	@Override
	public int calcHeight(World world, Random rand, BlockPos pos1) {
		int currDown = world.rand.nextInt(5) + 6;
		while (world.isAirBlock(pos1.down(currDown))) {
			if (currDown <= 1) {
				currDown = 1;
				break;
			}
			currDown -= rand.nextInt(3);
		}
		return currDown;
	}

	@Override
	public boolean onSetOreBlock(World world, BlockPos pos, Random rand) {
		if (isNotAirSurround(world, pos, rand)) {
			BlockPos up = world.getTopSolidOrLiquidBlock(pos);
			if (EMBlocks.enderOrchid.canSustainBush(world.getBlockState(up.down())) && world.isAirBlock(up) && rand.nextInt(3) != 0)
				setBlockAndNotifyAdequately(world, up, EMBlocks.enderOrchid.getDefaultState());
			return true;
		}
		else return false;
	}

	private boolean isNotAirSurround(World world, BlockPos pos, Random rand) {
		return Arrays.stream(EnumFacing.VALUES).allMatch(e -> !world.isAirBlock(pos.offset(e)));
	}
}