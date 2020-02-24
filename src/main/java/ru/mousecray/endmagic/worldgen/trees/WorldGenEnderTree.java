package ru.mousecray.endmagic.worldgen.trees;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

public abstract class WorldGenEnderTree extends WorldGenAbstractTree {
    private final Vec3i areaRequirementsMin;
    private final Vec3i areaRequirementsMax;

    public WorldGenEnderTree(boolean notify, Vec3i areaRequirementsMin, Vec3i areaRequirementsMax) {
        super(notify);
        this.areaRequirementsMin = areaRequirementsMin;
        this.areaRequirementsMax = areaRequirementsMax;
    }

    public boolean canGenerateThereAvaiable(World worldIn, BlockPos position) {
        return canGenerateThere(worldIn, position) && WorldGenUtils.areaAvailable(worldIn, position.add(areaRequirementsMin), position.add(areaRequirementsMax), ImmutableSet.of(Blocks.AIR, EMBlocks.enderSapling));
    }
    
    public boolean canGenerateThere(World worldIn, BlockPos position) {
        return EMUtils.isSoil(worldIn.getBlockState(position.down()), true, false, EndSoilType.GRASS, EndSoilType.DIRT);
    }
}