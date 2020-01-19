package ru.mousecray.endmagic.worldgen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

public abstract class WorldGenEnderTree extends WorldGenAbstractTree {
    private final Vec3i areaRequirementsMin;
    private final Vec3i areaRequirementsMax;

    public WorldGenEnderTree(boolean notify, Vec3i areaRequirementsMin, Vec3i areaRequirementsMax) {
        super(notify);
        System.out.println("test "+areaRequirementsMin + " " + areaRequirementsMax);
        this.areaRequirementsMin = areaRequirementsMin;
        this.areaRequirementsMax = areaRequirementsMax;
    }

    public boolean canGenerateThere(World worldIn, BlockPos position) {
        return worldIn.getBlockState(position.down()).getBlock() == Blocks.END_STONE && WorldGenUtils.areaAvailable(worldIn, position.add(areaRequirementsMin), position.add(areaRequirementsMax));
    }
}
