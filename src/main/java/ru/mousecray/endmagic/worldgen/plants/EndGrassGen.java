package ru.mousecray.endmagic.worldgen.plants;

import java.util.List;
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

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if(EMUtils.isSoil(world, pos.down(), true, EndSoilType.GRASS, EndSoilType.DIRT)) {
			List<BlockPos> existPos = EMUtils.isSoil(world, pos.add(-1, -1, -1), pos.add(2, -1, 2), true, EndSoilType.GRASS, EndSoilType.DIRT);
        	if (existPos.size() < 1) return false;
        	for (int i = 0; i < existPos.size(); ++i) {
        		int chance = rand.nextInt(100);
        		if (world.isAirBlock(existPos.get(i).up())) {
	        		if (chance > 95) world.setBlockState(existPos.get(i).up(), EMBlocks.blockCurseBush.getDefaultState(), 18);
	        		else if (chance > 80) world.setBlockState(existPos.get(i).up(), EMBlocks.enderOrchid.getDefaultState(), 18);
	        		else if (chance > 50) world.setBlockState(existPos.get(i).up(), EMBlocks.enderTallgrass.getDefaultState(), 18);
        		}
        	}
        	return true;
		}
		else return false;
	}
}