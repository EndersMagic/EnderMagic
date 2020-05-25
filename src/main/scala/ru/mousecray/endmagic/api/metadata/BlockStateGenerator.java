package ru.mousecray.endmagic.api.metadata;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockStateGenerator {
    protected List<IProperty<?>> properties = new ArrayList<>();
    protected List<IProperty<?>> excludedProperties = new ArrayList<>();
    protected List<PropertyFeature<?>> features = new ArrayList<>();
    protected Block block;
    private PropertyFeature<?> featureWithItemBlock;

    protected BlockStateGenerator(Block block) {
        this.block = block;
    }

    @Nonnull
    public static BlockStateGenerator create(Block block) {
        return new BlockStateGenerator(Preconditions.checkNotNull(block));
    }

    @Nonnull
    public BlockStateGenerator addProperties(IProperty<?>... properties) {
        this.properties.addAll(Arrays.asList(properties));
        return this;
    }

    public BlockStateGenerator excludeProperties(IProperty<?>... properties) {
        excludedProperties.addAll(Arrays.asList(properties));
        return this;
    }

    public BlockStateGenerator addFeature(@Nonnull PropertyFeature<?> feature, boolean hasCustomItemBlock) {
        Preconditions.checkNotNull(feature);
        if (hasCustomItemBlock) {
            Preconditions.checkArgument(featureWithItemBlock == null,
                    "BlockState can't contains two feature with custom ItemBlock. " +
                            "Exist feature: " + featureWithItemBlock + "; " +
                            "New feature: " + feature);
            featureWithItemBlock = feature;
        } else features.add(feature);
        return this;
    }

    public MetadataContainer buildContainer() {
        return new MetadataContainer(block, featureWithItemBlock, features, properties, excludedProperties);
    }
}