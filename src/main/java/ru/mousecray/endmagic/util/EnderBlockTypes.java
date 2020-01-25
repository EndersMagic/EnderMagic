package ru.mousecray.endmagic.util;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.blocks.BlockTypeBase;
import ru.mousecray.endmagic.blocks.trees.EMSapling;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.tileentity.TilePhantomAvoidingBlockBase;
import ru.mousecray.endmagic.worldgen.WorldGenDragonTree;
import ru.mousecray.endmagic.worldgen.WorldGenEnderTree;
import ru.mousecray.endmagic.worldgen.WorldGenPhantomTree;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

public class EnderBlockTypes {

    public static enum EnderTreeType implements IStringSerializable, EMSapling.SaplingThings, BlockTypeBase {
        DRAGON("dragon", MapColor.PURPLE, WorldGenDragonTree.class) {
            public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
                return Arrays.stream(EnumFacing.HORIZONTALS)
                        .map(pos::offset)
                        .map(worldIn::getBlockState)
                        .map(IBlockState::getBlock)
                        .anyMatch(Blocks.END_STONE::equals);
            }
        },
        NATURAL("natural", MapColor.BROWN, null),
        IMMORTAL("immortal", MapColor.EMERALD, null),
        PHANTOM("phantom", MapColor.SILVER, WorldGenPhantomTree.class) {
            @Override
            public boolean hasTileEntity(IBlockState state) {
                return state.getBlock() == EMBlocks.enderLog || state.getBlock() == EMBlocks.enderLeaves;
            }

            @Override
            public TileEntity createTileEntity(World world, IBlockState state) {
                return new TilePhantomAvoidingBlockBase();
            }

            @Override
            public EnumBlockRenderType getRenderType(IBlockState state) {
                return state.getBlock() == EMBlocks.enderLog || state.getBlock() == EMBlocks.enderLeaves
                        ? EnumBlockRenderType.ENTITYBLOCK_ANIMATED
                        : EnumBlockRenderType.MODEL;
            }
        };

        private final String name;
        private final MapColor mapColor;
        private final Class<? extends WorldGenEnderTree> generatorClass;
        private WorldGenEnderTree generator;

        public WorldGenEnderTree generator() {
            if (generator == null)
                try {
                    generator = generatorClass.getDeclaredConstructor(boolean.class).newInstance(true);
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            return generator;
        }

        EnderTreeType(String name, MapColor mapColor, Class<? extends WorldGenEnderTree> generator) {
            this.name = name;
            this.mapColor = mapColor;
            generatorClass = generator;
        }

        @Override
        public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
            generator().generate(worldIn, rand, pos);
        }

        public MapColor getMapColor() {
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

    public static enum EnderGroundType implements IStringSerializable {
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

        public MapColor getMapColor() {
            return mapColor;
        }

        public SoundType getSound() {
            return sound;
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

    public static enum EMBlockHalf implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom"),
        DOUBLE("double");

        private final String name;

        private EMBlockHalf(String name) {
            this.name = name;
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