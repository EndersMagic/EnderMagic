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

public class DragonTreeGen extends AbstractEnderTreeGen {
	
	public DragonTreeGen(boolean notify) {
		super(notify, ListBlock.ENDER_LOGS.getDefaultState().withProperty(EnderLogs.TYPE, EnderPlanks.EnumType.DRAGON), 
					ListBlock.ENDER_LEAVES.getDefaultState().withProperty(EnderLeaves.TYPE, EnderPlanks.EnumType.DRAGON)
					.withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int minTreeH = rand.nextInt(4)+4;
        int treeH = minTreeH + 3;
        int countStick = rand.nextInt(3)+1;
        int dis = minTreeH == 4 ? 1 : minTreeH / countStick;
        if(dis == 1 && countStick > 2) countStick = 2;
        IBlockState state = world.getBlockState(pos.down());
        boolean genPerm = true;
        
        if (pos.getY() >= 1 && pos.getY() + treeH + 1 <= world.getHeight() && state.getBlock() == Blocks.END_STONE) {
            for (int i = 1; i <= treeH + 1; ++i) {
              BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(pos);
              
              for (int x = -4; x <= 4 && genPerm; ++x) 
            	for (int z = -4; z <= 4 && genPerm; ++z) 
            		if (pos.getY()+i < world.getHeight()) { 
            			if(!this.isReplaceable(world, mpos.add(x, i, z))) genPerm = false; 
            		}
            		else genPerm = false;
            }

            if (!genPerm) return false;
            else {
            	state.getBlock().onPlantGrow(state, world, pos.down(), pos);
            	int lastIntY = 0;
                    
            	//Generate trunk
            	for(int y = 0; y < minTreeH; y++) {
            		setBlockN(world, pos.add(0, lastIntY, 0), metaWood);
            		lastIntY++;
            	}
            	//Generate leaves
            	setBlockN(world, pos.add(1, lastIntY-1, 1), metaLeaves);
            	setBlockN(world, pos.add(1, lastIntY-1, 0), metaLeaves);
            	setBlockN(world, pos.add(1, lastIntY-1, -1), metaLeaves);
            	setBlockN(world, pos.add(0, lastIntY-1, 1), metaLeaves);
            	setBlockN(world, pos.add(0, lastIntY-1, -1), metaLeaves);
            	setBlockN(world, pos.add(-1, lastIntY-1, 1), metaLeaves);
            	setBlockN(world, pos.add(-1, lastIntY-1, 0), metaLeaves);
            	setBlockN(world, pos.add(-1, lastIntY-1, -1), metaLeaves);
            	setBlockN(world, pos.add(0, lastIntY, 0), metaLeaves);
            	setBlockN(world, pos.add(1, lastIntY, 0), metaLeaves);
            	setBlockN(world, pos.add(-1, lastIntY, 0), metaLeaves);
            	setBlockN(world, pos.add(0, lastIntY, 1), metaLeaves);
            	setBlockN(world, pos.add(0, lastIntY, -1), metaLeaves);
            	setBlockN(world, pos.add(0, lastIntY+1, 0), metaLeaves);
                
            	BlockPos lastPosY = null;
            	
            	//Generate stick 1
            	setBlockN(world, pos.add(1, 1, 0), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
            	setBlockN(world, pos.add(2, 1, 0), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
            	setBlockN(world, pos.add(3, 2, 0), metaWood);
            	lastPosY = pos.add(3, 3, 0);
            	//Generate leaves 1
            	genLeaves(world, lastPosY);
            	
            	//Generate stick 2
            	if(countStick > 1) {
            		setBlockN(world, pos.add(-1, 1+dis, 0), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
                	setBlockN(world, pos.add(-2, 1+dis, 0), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.X));
                	setBlockN(world, pos.add(-3, 2+dis, 0), metaWood);
                	lastPosY = pos.add(-3, 3+dis, 0);
                	//Generate leaves 2
                	genLeaves(world, lastPosY);
            	}
            	
            	//Generate stick 3
            	if(countStick > 2) {
            		setBlockN(world, pos.add(0, 1+dis*2, 1), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
                	setBlockN(world, pos.add(0, 1+dis*2, 2), metaWood.withProperty(EnderLogs.LOG_AXIS, EnderLogs.EnumAxis.Z));
                	setBlockN(world, pos.add(0, 2+dis*2, 3), metaWood);
                	lastPosY = pos.add(0, 3+dis*2, 3);
                	//Generate leaves 3
                	genLeaves(world, lastPosY);
            	}
            	
            	return true;
            }
        }
        else return false;
	}
	
	private void genLeaves(World world, BlockPos pos) {	
    	setBlockN(world, pos, metaLeaves);
    	setBlockN(world, pos.add(1, -1, 0), metaLeaves);
    	setBlockN(world, pos.add(-1, -1, 0), metaLeaves);
    	setBlockN(world, pos.add(0, -1, 1), metaLeaves);
    	setBlockN(world, pos.add(0, -1, -1), metaLeaves);	
	}
}