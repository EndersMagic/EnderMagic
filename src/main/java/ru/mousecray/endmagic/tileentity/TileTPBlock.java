package ru.mousecray.endmagic.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileTPBlock extends TileEntity {

	private BlockPos toPos = null;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		if(toPos != null) {
			compound.setLong("toPos", toPos.toLong());
//			compound.setIntArray("toPos", new int[]{toPos.getX(), toPos.getY(), toPos.getZ()});
		}		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(compound != null && compound.hasKey("toPos")) {
//			int[] i = compound.getIntArray("toPos");
//			toPos = new BlockPos(i[0], i[1], i[2]);
			toPos = BlockPos.fromLong(compound.getLong("toPos"));
		}
	}
	
	public BlockPos getToPos() {
		if(toPos != null) return toPos;
		else return null;
	}
	
	public void setToPos(BlockPos toPos) {
		this.toPos = toPos;
	}
}
