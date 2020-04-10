package ru.mousecray.endmagic.util;

import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class RandomBlockPos extends BlockPos {
    public RandomBlockPos() {
        super(0, 0, 0);
    }

    private int x = 0;
    private int z = 0;

    @Override
    public int getX() {
        updateCoord(nx -> x = nx);
        return x;
    }

    private void updateCoord(Consumer<Integer> setCoord) {
        if ((System.currentTimeMillis() / 10L) % 300L == 0)
            setCoord.accept((int) (Math.random() * 1000 - 500));
    }

    @Override
    public int getZ() {
        updateCoord(nz -> z = nz);
        return z;
    }
}
