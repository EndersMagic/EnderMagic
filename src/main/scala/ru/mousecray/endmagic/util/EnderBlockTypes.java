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
        DRAGON("dragon", MapColor.PURPLE, WorldGenDragonTree.class),
        NATURAL("natural", MapColor.BROWN, WorldGenNaturalTree.class),
        IMMORTAL("immortal", MapColor.EMERALD, null),
        PHANTOM("phantom", MapColor.SILVER, WorldGenPhantomTree.class);

        private final String name;
        private final MapColor mapColor;
        private Class<? extends WorldGenEnderTree> generatorClass;
        private WorldGenEnderTree generator;

        EnderTreeType(String name, MapColor mapColor, @Nullable Class<? extends WorldGenEnderTree> generatorClass) {
            this.name = name;
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

}