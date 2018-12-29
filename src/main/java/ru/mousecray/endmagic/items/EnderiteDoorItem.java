package ru.mousecray.endmagic.items;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.EnderiteDoor;

public class EnderiteDoorItem extends Default {
	
	 private final Block block;

	public EnderiteDoorItem(Block block) {
		super("enderite_door_item", 4);
		this.block = block;
	}

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }
        else {
            IBlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(world, pos)) {
                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(world, pos)) {
                EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
                int i = enumfacing.getFrontOffsetX();
                int j = enumfacing.getFrontOffsetZ();
                boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
                placeDoor(world, pos, enumfacing, this.block, flag);
                SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
                world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }

    public static void placeDoor(World world, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
        BlockPos blockpos = pos.offset(facing.rotateY());
        BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
        int i = (world.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (world.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
        int j = (world.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (world.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
        boolean flag = world.getBlockState(blockpos1).getBlock() == door || world.getBlockState(blockpos1.up()).getBlock() == door;
        boolean flag1 = world.getBlockState(blockpos).getBlock() == door || world.getBlockState(blockpos.up()).getBlock() == door;

        if ((!flag || flag1) && j <= i) {
            if (flag1 && !flag || j < i) {
                isRightHinge = false;
            }
        }
        else {
            isRightHinge = true;
        }

        BlockPos blockpos2 = pos.up();
        boolean flag2 = world.isBlockPowered(pos) || world.isBlockPowered(blockpos2);
        IBlockState iblockstate = door.getDefaultState().withProperty(EnderiteDoor.FACING, facing).withProperty(EnderiteDoor.HINGE, isRightHinge ? EnderiteDoor.EnumHingePosition.RIGHT : EnderiteDoor.EnumHingePosition.LEFT).withProperty(EnderiteDoor.POWERED, Boolean.valueOf(flag2)).withProperty(EnderiteDoor.OPEN, Boolean.valueOf(flag2));
        world.setBlockState(pos, iblockstate.withProperty(EnderiteDoor.HALF, EnderiteDoor.EnumDoorHalf.LOWER), 2);
        world.setBlockState(blockpos2, iblockstate.withProperty(EnderiteDoor.HALF, EnderiteDoor.EnumDoorHalf.UPPER), 2);
        world.notifyNeighborsOfStateChange(pos, door, false);
        world.notifyNeighborsOfStateChange(blockpos2, door, false);
    }
}
