package ru.mousecray.endmagic.worldgen.plants;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class EndGrassGen extends WorldGenerator {

	public EndGrassGen() {
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		return false;
	}

}
