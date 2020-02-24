package ru.mousecray.endmagic.worldgen.plants;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.mousecray.endmagic.init.EMBlocks;

public class EndGrassGen extends WorldGenerator {

	public EndGrassGen() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		for (int f = 0; f < 25; ++f) {
			int x = rand.nextInt(16);
			int z = rand.nextInt(16);
			BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).down();
			boolean flag = EMBlocks.enderTallgrass.canSustainBush(world.getBlockState(pos2));
			// System.out.println("Start");
			if (flag) {
				int chance = rand.nextInt(20);
				// System.out.println("/tp " + pos2.up().getX() + " " + pos2.up().getY() + " " +
				// pos2.up().getZ());
				if (chance == 0) setBlockAndNotifyAdequately(world, pos2.up(), EMBlocks.enderOrchid.getDefaultState());
				else /* if (chance > 50) */ setBlockAndNotifyAdequately(world, pos2.up(), EMBlocks.enderTallgrass.getDefaultState());
			}
		}
		return true;
	}
}