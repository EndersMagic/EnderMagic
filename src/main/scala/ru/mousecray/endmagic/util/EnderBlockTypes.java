package ru.mousecray.endmagic.util;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.gameobj.blocks.BlockTypeBase;
import ru.mousecray.endmagic.gameobj.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.gameobj.blocks.trees.EMSapling;
import ru.mousecray.endmagic.gameobj.tileentity.TilePhantomAvoidingBlockBase;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.worldgen.trees.WorldGenDragonTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenEnderTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenNaturalTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenPhantomTree;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EnderBlockTypes {

    public enum EnderTreeType implements IStringSerializable, EMSapling.SaplingThings, BlockTypeBase {
        DRAGON("dragon", MapColor.PURPLE, WorldGenDragonTree.class) {
            @Override
            public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
                return Arrays.stream(EnumFacing.HORIZONTALS)
                        .map(pos::offset)
                        .map(worldIn::getBlockState)
                        //TODO: add custom end grass and remove STONE from this
                        .anyMatch(state -> EMUtils.isSoil(state, EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS));
            }

            @Override
            public boolean hasTickRandomly() {
                return true;
            }

            @Override
            public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
                if (state.getBlock() == EMBlocks.enderLog) {
                    if (world.provider instanceof WorldProviderEnd) {
                        DragonFightManager dragonfightmanager = ((WorldProviderEnd) world.provider).getDragonFightManager();
                        if (
                                dragonfightmanager.dragonKilled && // if dragon dead
                                        world.isAirBlock(pos.down()) &&
                                        rand.nextInt(20) == 15 && //rand
                                        pos.getX() < 300 && pos.getX() > -300 && //is cenreal island
                                        pos.getZ() < 300 && pos.getZ() > -300
                        ) {
                            EntityFallingBlock entity = new EntityFallingBlock(world, pos.getX(), pos.getY(), pos.getZ(), world.getBlockState(pos));
                            entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                            entity.moveToBlockPosAndAngles(pos, 0F, 0F);
                            entity.motionX = 0.0D;
                            entity.motionZ = 0.0D;
                            entity.setHurtEntities(true);
                            world.spawnEntity(entity);
                        }
                    }
                } else if (state.getBlock() instanceof EMLeaves) {
                    if (!world.isRemote) {
                        if (world.isAreaLoaded(pos, 2)) {
                            if (!state.getValue(EMLeaves.CHECK_DECAY) || !state.getValue(EMLeaves.DECAYABLE)) return;
                            //TODO: Фиг его знает
                            if (findingArea(pos).noneMatch(pos1 -> world.getBlockState(pos1).getBlock().canSustainLeaves(state, world, pos))) {
                                state.getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
                                world.setBlockToAir(pos);
                            }
                        }
                    }
                }
            }

            private Stream<BlockPos> findingArea(BlockPos pos) {
                return IntStream.range(-5, 5)
                        .mapToObj(x ->
                                IntStream.range(-5, 5)
                                        .mapToObj(y ->
                                                IntStream.range(-5, 5)
                                                        .mapToObj(z ->
                                                                pos.add(x, y, z))).flatMap(Function.identity())).flatMap(Function.identity());
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
        private Class<? extends WorldGenEnderTree> generatorClass;
        private WorldGenEnderTree generator;

        EnderTreeType(String name, MapColor mapColor, @Nullable Class<? extends WorldGenEnderTree> generatorClass) {
            this.name = name;
            this.mapColor = mapColor;
            this.generatorClass = generatorClass;
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
        public WorldGenEnderTree getGenerator() {
            return generator();
        }

        @Override
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

    public enum EnderGroundType implements IStringSerializable, BlockTypeBase {
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

    public enum EMBlockHalf implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom"),
        DOUBLE("double");

        private final String name;

        EMBlockHalf(String name) {
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