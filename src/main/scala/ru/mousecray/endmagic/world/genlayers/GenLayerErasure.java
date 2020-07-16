package ru.mousecray.endmagic.world.genlayers;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GenLayerErasure extends GenLayer {

    private final int around;
    private final int replacement;

    public GenLayerErasure(long seed, GenLayer parent, int around, int replacement) {
        super(seed);
        this.around = around;
        this.replacement = replacement;
        this.parent = parent;
    }

    public GenLayerErasure(long seed, GenLayer parent, int around) {
        this(seed, parent, around, 0);
    }


    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] inLayer = parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] outLayer = IntCache.getIntCache(areaWidth * areaHeight);

        //copy inLayer to outLayer
        for (int i = 0; i < areaHeight; ++i)
            for (int j = 0; j < areaWidth; ++j)
                outLayer[j + i * areaWidth] = inLayer[j + i * areaWidth];

        //check ad erase
        for (int i = 0; i < areaHeight; ++i)
            for (int j = 0; j < areaWidth; ++j)
                if (outLayer[j + i * areaWidth] == around)
                    for (int ii = max(i - 1, 0); ii <= min(i + 1, areaHeight - 1); ii++)
                        for (int jj = max(j - 1, 0); jj <= min(j + 1, areaWidth - 1); jj++)
                            checkAndErase(outLayer, i + ii, j + jj, areaWidth);


        return outLayer;
    }

    private void checkAndErase(int[] outLayer, int i, int j, int areaWidth) {
        if (outLayer[j + i * areaWidth] != around)
            outLayer[j + i * areaWidth] = replacement;
    }
}
