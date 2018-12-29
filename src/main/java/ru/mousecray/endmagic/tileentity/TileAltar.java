package ru.mousecray.endmagic.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileAltar extends TileEntity implements ITickable {
	
	private int ticks = 0;
	private boolean down = false;
	private float pos = -6F;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		ticks = compound.getInteger("ticks");
		down = compound.getBoolean("down");
		pos = compound.getFloat("pos");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("ticks", ticks);
		compound.setBoolean("down", down);
		compound.setFloat("pos", pos);
		super.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void update() {
		if(ticks >= 20) ++ticks;
		else ticks = 0;
		
		if(ticks / 10 % 5 != 0) {
			return;
		}
		
		if(!down) {
			if(pos > -6.5F) pos -= 0.1F;
			else if(pos > -7F) pos -= 0.25F;
			else if(pos > -9F) pos -= 0.2F;
			else if(pos > -9.5F) pos -= 0.25F;
			else if(pos > -10F) pos -= 0.1F;
			else if(pos <= -10F) {
				pos = -10F;
				down = true;
			}
		}
		else {
			if (pos < -9.5F) pos += 0.1F;
			else if(pos < -9F) pos += 0.25F;
			else if(pos < -7F) pos += 0.2F;
			else if(pos < -6.5F) pos += 0.25F;
			else if(pos < -6F) pos += 0.1F;
			else if(pos >= -6F){
				pos = -6F;
				down = false;
			}
		}
	}
	
	public int getTicks() {
		return ticks;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public float getFloat() {
		return pos;
	}
	
	public void setFloat(float pos) {
		this.pos = pos;
	}
}