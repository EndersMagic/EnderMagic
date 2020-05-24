package ru.mousecray.endmagic.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.metadata.IFeaturesList;
import ru.mousecray.endmagic.api.metadata.PropertyFeature;
import ru.mousecray.endmagic.gameobj.blocks.trees.EnderSapling;
import ru.mousecray.endmagic.worldgen.trees.WorldGenDragonTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenEnderTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenNaturalTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenPhantomTree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

import static net.minecraft.block.Block.FULL_BLOCK_AABB;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EnderBlockTypes {

    public static final PropertyFeature<EnderTreeType> TREE_TYPE = PropertyFeature.createProperty("type", EnderTreeType.class);
    public static final PropertyFeature<EnderGroundType> GROUND_TYPE = PropertyFeature.createProperty("type", EnderGroundType.class);
    public static final PropertyFeature<EMBlockHalf> BLOCK_HALF = PropertyFeature.createProperty("state", EMBlockHalf.class);
    public static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    public static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    public enum EnderTreeType implements EnderSapling.SaplingThings, IFeaturesList {
        DRAGON("dragon", MapColor.PURPLE, true, WorldGenDragonTree.class) {
            @Override
            public boolean canPlaceBlockAt(World world, BlockPos pos) {
                return Arrays.stream(EnumFacing.HORIZONTALS)
                        .map(pos::offset)
                        .map(world::getBlockState)
                        //TODO: add custom end grass and remove STONE from this
                        .anyMatch(state -> EMUtils.isSoil(state, EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS));
            }
        },
        NATURAL("natural", MapColor.BROWN, true, WorldGenNaturalTree.class),
        IMMORTAL("immortal", MapColor.EMERALD, true, null),
        PHANTOM("phantom", MapColor.SILVER, false, WorldGenPhantomTree.class);

        private final String name;
        private final MapColor mapColor;
        private Class<? extends WorldGenEnderTree> generatorClass;
        private WorldGenEnderTree generator;
        private final boolean opaque;

        EnderTreeType(String name, MapColor mapColor, boolean opaque, @Nullable Class<? extends WorldGenEnderTree> generatorClass) {
            this.name = name;
            this.mapColor = mapColor;
            this.generatorClass = generatorClass;
            this.opaque = opaque;
        }

        private WorldGenEnderTree generator() {
            if (generator == null) try {
                generator = generatorClass.getDeclaredConstructor(boolean.class).newInstance(true);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return generator;
        }

        @Override
        public boolean isFullCube() {
            return opaque;
        }

        @Override
        public boolean isOpaqueCube() {
            return opaque;
        }

        @Override
        public WorldGenEnderTree getGenerator() {
            return generator();
        }

        @Override
        public MapColor getMapColor(IBlockAccess world, BlockPos pos) {
            return mapColor;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum EnderGroundType implements IFeaturesList {
        LIVE("live", MapColor.BLUE, SoundType.GROUND),
        DEAD("dead", MapColor.GRAY, SoundType.SAND),
        FROZEN("frozen", MapColor.DIAMOND, SoundType.SNOW);

        private final String name;
        private final MapColor mapColor;
        private final SoundType sound;

        EnderGroundType(String name, MapColor mapColor, SoundType sound) {
            this.name = name;
            this.mapColor = mapColor;
            this.sound = sound;
        }

        @Override
        public MapColor getMapColor(IBlockAccess world, BlockPos pos) {
            return mapColor;
        }

        @Override
        public SoundType getSoundType(World world, BlockPos pos, Entity entity) {
            return sound;
        }

        @Override
        public String toString() {
            return name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }

    public enum EMBlockHalf implements IFeaturesList {
        TOP("top", 0, AABB_TOP_HALF, false, true, 1),
        BOTTOM("bottom", 0, AABB_BOTTOM_HALF, false, false, 1),
        DOUBLE("double", 255, FULL_BLOCK_AABB, true, true, 2);

        private final String name;
        private final int lightOpacity;
        private final AxisAlignedBB aabb;
        private final boolean opaque;
        private final boolean topSolid;
        private final int dropCount;

        EMBlockHalf(String name, int lightOpacity, AxisAlignedBB aabb, boolean opaque, boolean topSolid, int dropCount) {
            this.name = name;
            this.lightOpacity = lightOpacity;
            this.aabb = aabb;
            this.opaque = opaque;
            this.topSolid = topSolid;
            this.dropCount = dropCount;
        }

        @Override
        public int quantityDropped(int fortune, Random rand) {
            return dropCount;
        }

        @Override
        public boolean isTopSolid() {
            return topSolid;
        }

        @Override
        public int getLightOpacity(IBlockAccess world, BlockPos pos) {
            return lightOpacity;
        }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockAccess world, BlockPos pos) {
            return aabb;
        }

        @Override
        public boolean isOpaqueCube() {
            return opaque;
        }

        @Override
        public boolean isFullCube() {
            return opaque;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}