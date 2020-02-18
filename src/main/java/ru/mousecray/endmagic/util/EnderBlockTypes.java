package ru.mousecray.endmagic.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.annotation.Nullable;

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
import ru.mousecray.endmagic.worldgen.WorldGenNaturalTree;
import ru.mousecray.endmagic.worldgen.WorldGenPhantomTree;

public class EnderBlockTypes {

    public static enum EnderTreeType implements IStringSerializable, EMSapling.SaplingThings, BlockTypeBase {
        DRAGON("dragon", MapColor.PURPLE, WorldGenDragonTree.class) {
            @Override
			public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
                return Arrays.stream(EnumFacing.HORIZONTALS)
                        .map(pos::offset)
                        .map(worldIn::getBlockState)
                        .map(IBlockState::getBlock)
                        .anyMatch(Blocks.END_STONE::equals);
            }
        },
        NATURAL("natural", MapColor.BROWN, WorldGenNaturalTree.class),
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

            @Override
			public boolean isFullCube() {
                return false;
            }

            @Override
			public boolean isOpaqueCube() {
                return false;
            }
        };

        private final String name;
        private final MapColor mapColor;
        private WorldGenEnderTree generator;

        EnderTreeType(String name, MapColor mapColor, @Nullable Class<? extends WorldGenEnderTree> generatorClass) {
            this.name = name;
            this.mapColor = mapColor;
            if (generatorClass != null) {
                try {
                    generator = generatorClass.getDeclaredConstructor(boolean.class).newInstance(true);
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public WorldGenEnderTree getGenerator() { 
        	return generator; 
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

    public static enum EnderGroundType implements IStringSerializable, BlockTypeBase {
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