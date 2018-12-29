package ru.mousecray.endmagic.world.gen.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.mousecray.endmagic.blocks.ListBlock;

public class EnderFarmGen extends WorldGenerator {

	@Override
	// I feel sad now :(
	public boolean generate(World world, Random rand, BlockPos pos) {
		
        IBlockState state = world.getBlockState(pos.down());
        
        if (pos.getY() >= 1 && pos.getY() + 13 + 1 <= world.getHeight() && state.getBlock() == Blocks.END_STONE) {
        	
            boolean genPerm = true;
            
        	BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(pos.down());
        	for(int i = -3; i < 3; i++) for(int k = -3; k < 3; k++) //EM: Don't indent below
	        if(world.getBlockState(mpos.setPos(i, 0, k)) != Blocks.END_STONE.getDefaultState() && 
	        world.getBlockState(mpos.setPos(i, 1, k)) != Blocks.AIR.getDefaultState()) genPerm = false;
	        
	        if(!genPerm) return false;
	        else {
	        	for(int x = -2; x < 2; x++) for(int z = -2; z < 2; z++) //EM: Don't indent below
	        	if(rand.nextInt(100) <= 30) world.setBlockState(pos.add(x, 0, z), ListBlock.ENDER_GRASS.getDefaultState(), 18);
	        	
//	        	for(int x = 0; x < 7; x++) for(int z = 0; z < 7; z++) //EM: Don't indent below
//				if(!(x > 0 && x < 6) || !(z > 0 && z < 6)) world.setBlockState(pos.add(x-3, 0, z-3), Blocks.OAK_FENCE.getDefaultState(), 18);
            	return true;
	        }
         	
        } else return false;
	}
}
