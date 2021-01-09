package ru.mousecray.endmagic.tileentity;

public interface ByBlockNotifiable {
    public void neighborChanged();
    public void breakBlock();
}
