package ru.mousecray.endmagic.world.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.EnderLeaves;
import ru.mousecray.endmagic.blocks.EnderLogs;
import ru.mousecray.endmagic.blocks.EnderPlanks;
import ru.mousecray.endmagic.blocks.ListBlock;

public class NaturalTreeGen extends AbstractEnderTreeGen {

	public NaturalTreeGen(boolean notify) {
		super(notify, ListBlock.ENDER_LOGS.getDefaultState().withProperty(EnderLogs.TYPE, EnderPlanks.EnumType.NATURAL), 
				ListBlock.ENDER_LEAVES.getDefaultState().withProperty(EnderLeaves.TYPE, EnderPlanks.EnumType.NATURAL)
				.withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
	}
	
	//Easy :)
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		
        IBlockState state = world.getBlockState(pos.down());
        
        if (pos.getY() >= 1 && pos.getY() + 13 + 1 <= world.getHeight() && state.getBlock() == Blocks.END_STONE) {
        	state.getBlock().onPlantGrow(state, world, pos.down(), pos);
        	
        	//Generate trunk
        	setBlockN(world, pos.add(0, -1, 0), metaWood);
        	setBlockN(world, pos.add(0, -1, -1), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(0, 0, -2), metaWood);
        	setBlockN(world, pos.add(0, 1, -2), metaWood);
        	setBlockN(world, pos.add(0, 2, -3), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(1, 2, -4), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
        	setBlockN(world, pos.add(2, 3, -4), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
        	setBlockN(world, pos.add(3, 3, -4), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
        	setBlockN(world, pos.add(3, 4, -3), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(3, 4, -2), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(3, 5, -1), metaWood);
        	setBlockN(world, pos.add(3, 6, 0), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(2, 6, 1), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
        	setBlockN(world, pos.add(1, 7, 1), metaWood);
        	setBlockN(world, pos.add(0, 8, 1), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
        	setBlockN(world, pos.add(-1, 8, 0), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(-1, 9, -1), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(-1, 9, -2), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	setBlockN(world, pos.add(-1, 10, -3), metaWood);
        	setBlockN(world, pos.add(-1, 11, -4), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
        	
        	//Generate leaves
        	BlockPos pos2 = pos.add(-1, 11, -4);
        	setBlockN(world, pos2.add(0, 2, 0), metaLeaves);
        	setBlockN(world, pos2.add(0, 1, 0), metaLeaves);
        	setBlockN(world, pos2.add(1, 1, 0), metaLeaves);
         	setBlockN(world, pos2.add(-1, 1, 0), metaLeaves);
         	setBlockN(world, pos2.add(0, 1, 1), metaLeaves);
         	setBlockN(world, pos2.add(0, 1, -1), metaLeaves);
         	setBlockN(world, pos2.add(1, 0, 0), metaLeaves);
         	setBlockN(world, pos2.add(2, 0, 0), metaLeaves);
         	setBlockN(world, pos2.add(0, 0, 1), metaLeaves);
         	setBlockN(world, pos2.add(0, 0, 2), metaLeaves);
         	setBlockN(world, pos2.add(-1, 0, 0), metaLeaves);
         	setBlockN(world, pos2.add(-2, 0, 0), metaLeaves);
         	setBlockN(world, pos2.add(0, 0, -1), metaLeaves);
         	setBlockN(world, pos2.add(0, 0, -2), metaLeaves);
         	setBlockN(world, pos2.add(1, 0, 1), metaLeaves);
         	setBlockN(world, pos2.add(1, 0, -1), metaLeaves);
         	setBlockN(world, pos2.add(-1, 0, 1), metaLeaves);
         	setBlockN(world, pos2.add(-1, 0, -1), metaLeaves);
         	setBlockN(world, pos2.add(0, -1, 0), metaLeaves);
         	setBlockN(world, pos2.add(0, -1, -1), metaLeaves);
         	setBlockN(world, pos2.add(1, -1, 0), metaLeaves);
         	setBlockN(world, pos2.add(-1, -1, 0), metaLeaves);
         	
         	return true;
         	
        } else return false;
	}
}
