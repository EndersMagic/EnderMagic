package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.worldgen.WorldGenDragonTree;

import java.util.Arrays;
import java.util.Random;

public enum EnderTreeType implements IStringSerializable, EMSapling.SaplingThings {
    DRAGON("dragon", MapColor.PURPLE) {
        public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
            return Arrays.stream(EnumFacing.HORIZONTALS)
                    .map(pos::offset)
                    .map(worldIn::getBlockState)
                    .map(IBlockState::getBlock)
                    .anyMatch(Blocks.END_STONE::equals);
        }

        @Override
        public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
            new WorldGenDragonTree(true).generate(worldIn, rand, pos);
        }
    },
    NATURAL("natural", MapColor.BLUE),
    IMMORTAL("immortal", MapColor.EMERALD),
    PHANTOM("phantom", MapColor.SILVER);

    private final String name;
    private final MapColor mapColor;

    EnderTreeType(String name, MapColor mapColor) {
        this.name = name;
        this.mapColor = mapColor;
    }

    @Override
    public String getName() {
        return name;
    }
}
