package ru.mousecray.endmagic.worldgen.plants;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.init.EMBlocks;

public class EndGrassGen extends WorldGenerator {

	public EndGrassGen() {
		super(false);
	}

	@SuppressWarnings("unused")
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
        for (int f = 0; f < 64; ++f) {
    		int x = rand.nextInt(8) - rand.nextInt(8);
    		int y = rand.nextInt(4) - rand.nextInt(4);
    		int z = rand.nextInt(8) - rand.nextInt(8);
    		BlockPos pos2 = pos.add(x, -1, z);
			boolean flag = EMUtils.isSoil(world.getBlockState(pos2), true, false, EndSoilType.GRASS, EndSoilType.DIRT);
			System.out.println("Start");
			int chance = rand.nextInt(100);
			if (flag) {
				System.out.println("/tp " + pos2.up().getX() + " " + pos2.up().getY() + " " + pos2.up().getZ());
				if (chance > 80) world.setBlockState(pos2.up(), EMBlocks.enderOrchid.getDefaultState(), 18);
				else if (chance > 50) world.setBlockState(pos2.up(), EMBlocks.enderTallgrass.getDefaultState(), 18);
			}
			return true;
		}
        return false;
	}
}