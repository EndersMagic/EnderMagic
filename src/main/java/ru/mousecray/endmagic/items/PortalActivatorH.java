package ru.mousecray.endmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.ListBlock;
import ru.mousecray.endmagic.tileentity.TileTPBlockH;

public class PortalActivatorH extends ActivatorBase {
	
	public PortalActivatorH() {
		super("portal_activatorh");
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			ItemStack itemstack = player.getHeldItem(hand);
			if(!itemstack.hasTagCompound()) {
				if(world.getBlockState(pos).getBlock() == ListBlock.TPH) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setIntArray("position", new int[]{pos.getX(), pos.getY(), pos.getZ()});
					itemstack.setTagCompound(nbt);
				}
			}
			else {
				if(world.getBlockState(pos).getBlock() == ListBlock.TPH) {
					if(world.getTileEntity(pos) instanceof TileTPBlockH) {
						TileTPBlockH tile = (TileTPBlockH)world.getTileEntity(pos);
						int[] toPosM = itemstack.getTagCompound().getIntArray("position");
						BlockPos toPos = new BlockPos(toPosM[0], toPosM[1], toPosM[2]);
						if(!(pos.equals(toPos))) {	
							if(tile.getToPos() == null) {
								tile.setToPos(toPos);
								if(itemstack.getCount() > 1) itemstack.shrink(1);
								else player.inventory.removeStackFromSlot(player.inventory.currentItem);
							}
							else player.sendMessage(new TextComponentTranslation("tooltip.portal_activators.already_connected"));
						}
						else player.sendMessage(new TextComponentTranslation("tooltip.portal_activators.point_of_departure"));
					}
				}
			}
		}
		return EnumActionResult.SUCCESS;
	}
}