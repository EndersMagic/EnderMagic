package ru.mousecray.endmagic.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class WorldGenNaturalTree extends WorldGenAbstractTree {
	public WorldGenNaturalTree(boolean notify) {
		super(notify);
	}
	
	//fill 1724 15 -967 1713 0 -959 endmagic:ender_tree_log 13 replace endmagic:ender_tree_log
    private IBlockState enderLog = EMBlocks.enderLog.getDefaultState().withProperty(EMBlocks.enderLog.getVariantType(), EnderBlockTypes.EnderTreeType.NATURAL);
    private IBlockState enderLeaves = EMBlocks.enderLeaves.getDefaultState().withProperty(EMBlocks.enderLeaves.getVariantType(), EnderBlockTypes.EnderTreeType.NATURAL);

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		return false;
	}

}
