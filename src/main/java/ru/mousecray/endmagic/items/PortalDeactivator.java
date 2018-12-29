package ru.mousecray.endmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.ListBlock;
import ru.mousecray.endmagic.tileentity.TileTPBlock;
import ru.mousecray.endmagic.tileentity.TileTPBlockH;

public class PortalDeactivator extends Default {

	public PortalDeactivator() {
		super("portal_deactivator", 1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) if(world.getBlockState(pos).getBlock() == ListBlock.TP || world.getBlockState(pos).getBlock() == ListBlock.TPH) 
			if(world.getTileEntity(pos) instanceof TileTPBlock) {
				TileTPBlock tile = (TileTPBlock)world.getTileEntity(pos);
				if(tile.getToPos() != null) {
					tile.setToPos(null);
					player.sendMessage(new TextComponentTranslation("tooltip.portal_deactivator.disconnected"));
					if(!player.isCreative()) player.inventory.removeStackFromSlot(player.inventory.currentItem);
				}
			}
			else if(world.getTileEntity(pos) instanceof TileTPBlockH) {
				TileTPBlockH tile = (TileTPBlockH)world.getTileEntity(pos);
				if(tile.getToPos() != null) {
					tile.setToPos(null);
					player.sendMessage(new TextComponentTranslation("tooltip.portal_deactivator.disconnected"));
					if(!player.isCreative()) player.inventory.removeStackFromSlot(player.inventory.currentItem);
				}
			}
		return EnumActionResult.SUCCESS;
	}
}
