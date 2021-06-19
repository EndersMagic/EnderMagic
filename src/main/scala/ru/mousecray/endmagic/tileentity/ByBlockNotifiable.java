package ru.mousecray.endmagic.tileentity;

public interface ByBlockNotifiable {
    void neighborChanged();
    void breakBlock();
}
