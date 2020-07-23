package ru.mousecray.endmagic.util;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;
import ru.mousecray.endmagic.worldgen.trees.WorldGenDragonTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenEnderTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenNaturalTree;
import ru.mousecray.endmagic.worldgen.trees.WorldGenPhantomTree;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class EnderBlockTypes {

    public static final PropertyEnum<EnderTreeType> TREE_TYPE = PropertyEnum.create("type", EnderTreeType.class);
    public static final PropertyEnum<EnderGroundType> GROUND_TYPE = PropertyEnum.create("type", EnderGroundType.class);

    public static enum EnderTreeType implements IStringSerializable {
        DRAGON(MapColor.PURPLE, WorldGenDragonTree.class),
        NATURAL(MapColor.BROWN, WorldGenNaturalTree.class),
        IMMORTAL(MapColor.EMERALD, null),
        PHANTOM(MapColor.SILVER, WorldGenPhantomTree.class);

        private final MapColor mapColor;
        private Class<? extends WorldGenEnderTree> generatorClass;
        private WorldGenEnderTree generator;

        EnderTreeType(MapColor mapColor, @Nullable Class<? extends WorldGenEnderTree> generatorClass) {
            this.mapColor = mapColor;
            this.generatorClass = generatorClass;
        }

        public WorldGenEnderTree generator() {
            if (generator == null) try {
                generator = generatorClass.getDeclaredConstructor(boolean.class).newInstance(true);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return generator;
        }

        public MapColor getMapColor() {
            return mapColor;
        }

        @Override
        public String toString() {
            return name();
        }

        @Override
        public String getName() {
            return name();
        }
    }

    public static enum EnderGroundType implements IStringSerializable {
        LIVE(MapColor.BLUE, SoundType.GROUND),
        DEAD(MapColor.GRAY, SoundType.SAND),
        FROZEN(MapColor.DIAMOND, SoundType.SNOW);

        private final MapColor mapColor;
        private final SoundType sound;

        EnderGroundType(MapColor mapColor, SoundType sound) {
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
            return name();
        }

        @Override
        public String getName() {
            return name();
        }
    }

}