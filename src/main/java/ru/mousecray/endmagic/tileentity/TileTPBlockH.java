package ru.mousecray.endmagic.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileTPBlockH extends TileEntity {
	
	private BlockPos toPos = null;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		if(toPos != null) {
			compound.setLong("toPos", toPos.toLong());
		}	
		return compound;
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(compound != null && compound.hasKey("toPos")) {
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