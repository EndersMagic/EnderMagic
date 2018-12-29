package ru.mousecray.endmagic.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.items.ListItem;

public class EnderiteDoor extends BlockDoor {

	public EnderiteDoor() {
		super(Material.IRON);
		setUnlocalizedName("enderite_door");
		setRegistryName("enderite_door");
		setHardness(5F);
		setResistance(35F);
		setHarvestLevel("pickaxe", 2);
		disableStats();
	}

    private int getCloseSound() {
        return 1011;
    }

    private int getOpenSound() {
        return 1005;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (state.getValue(HALF) == EnumDoorHalf.UPPER) {
            BlockPos blockpos = pos.down();
            IBlockState iblockstate = world.getBlockState(blockpos);

            if (iblockstate.getBlock() != this) world.setBlockToAir(pos);
            else if (block != this) iblockstate.neighborChanged(world, blockpos, block, fromPos);
        }
        else {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = world.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this) {
                world.setBlockToAir(pos);
                flag1 = true;
            }

            if (!world.getBlockState(pos.down()).isSideSolid(world,  pos.down(), EnumFacing.UP)) {
                world.setBlockToAir(pos);
                flag1 = true;

                if (iblockstate1.getBlock() == this) world.setBlockToAir(blockpos1);
            }

            if (flag1 && !world.isRemote) {
            	this.dropBlockAsItem(world, pos, state, 0);
            	EntityEnderman entity = new EntityEnderman(world);
            	entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
            	world.spawnEntity(entity);
            }
            else {
                boolean flag = world.isBlockPowered(pos) || world.isBlockPowered(blockpos1);

                if (block != this && (flag || block.getDefaultState().canProvidePower()) && flag != ((Boolean)iblockstate1.getValue(POWERED)).booleanValue()) {
                    world.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, Boolean.valueOf(flag)), 2);

                    if (flag != ((Boolean)state.getValue(OPEN)).booleanValue()) {
                        world.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(flag)), 2);
                        world.markBlockRangeForRenderUpdate(pos, pos);
                        world.playEvent((EntityPlayer)null, flag ? this.getOpenSound() : this.getCloseSound(), pos, 0);
                    }
                }
            }
        }
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : ListItem.ENDERITE_DOOR_ITEM;
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ListItem.ENDERITE_DOOR_ITEM);
    }
    
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
    	IBlockState iblockstate = pos.equals(blockpos) ? state : world.getBlockState(blockpos);

    	if (iblockstate.getBlock() != this) return false;
    	else {
    		state = iblockstate.cycleProperty(OPEN);
    		world.setBlockState(blockpos, state, 10);
    		world.markBlockRangeForRenderUpdate(blockpos, pos);
    		world.playEvent(player, ((Boolean)state.getValue(OPEN)).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    		return true;
    	}
    }
    
    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        BlockPos blockpos = pos.down();
        BlockPos blockpos1 = pos.up();

        if (player.capabilities.isCreativeMode && state.getValue(HALF) == EnumDoorHalf.UPPER && world.getBlockState(blockpos).getBlock() == this) {
            world.setBlockToAir(blockpos);
            if(!world.isRemote) {
	    		EntityEnderman entity = new EntityEnderman(world);
	    		entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
	    		world.spawnEntity(entity);
            }
        }

        if (state.getValue(HALF) == EnumDoorHalf.LOWER && world.getBlockState(blockpos1).getBlock() == this) {
            if (player.capabilities.isCreativeMode) {
                world.setBlockToAir(pos);
                if(!world.isRemote) {
	        		EntityEnderman entity = new EntityEnderman(world);
	        		entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
	        		world.spawnEntity(entity);
                }
            }

            world.setBlockToAir(blockpos1);
        }
    }
	
//	@Override
//	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
//		if(entity instanceof EntityPlayer && entity.isCollidedHorizontally) {
//			if(entity.onGround) {
//				EnumFacing facing = entity.getHorizontalFacing();
//				if(state.getValue(FACING).equals(EnumFacing.NORTH)) {
//					double x = pos.getX()+0.5;
//					double z = pos.getZ();
//					if(entity.getDistance(x, pos.getY(), z) < 0.1) {
//						entity.setPositionAndUpdate(x + world.rand.nextInt(8)-4 - 50, pos.getY() + world.rand.nextInt(8)-4, z + world.rand.nextInt(8)-4);
//					}
//				}
//				else if(state.getValue(FACING).equals(EnumFacing.SOUTH)) {
//					double x = pos.getX()+0.5;
//					double z = pos.getZ();
//					if(entity.getDistance(x, pos.getY(), z) < 0.1) {
//						entity.setPositionAndUpdate(x + world.rand.nextInt(8)-4 + 50, pos.getY() + world.rand.nextInt(8)-4, z + world.rand.nextInt(8)-4);					
//					}
//				}
//				else if(state.getValue(FACING).equals(EnumFacing.EAST)) {
//					double x = pos.getX();
//					double z = pos.getZ()+0.5;
//					if(entity.getDistance(x, pos.getY(), z) < 0.1) {
//						entity.setPositionAndUpdate(x + world.rand.nextInt(8)-4, pos.getY() + world.rand.nextInt(8)-4, z + world.rand.nextInt(8)-4 - 50);					
//					}
//				}
//				else {
//					double x = pos.getX();
//					double z = pos.getZ()+0.5;
//					if(entity.getDistance(x, pos.getY(), z) < 0.1) {
//						entity.setPositionAndUpdate(x + world.rand.nextInt(8)-4, pos.getY() + world.rand.nextInt(8)-4, z + world.rand.nextInt(8)-4 + 50);					
//					}
//				}
//			}
//		}
//	}
}