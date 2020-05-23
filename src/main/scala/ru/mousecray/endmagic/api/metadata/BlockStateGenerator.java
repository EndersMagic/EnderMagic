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
//        Preconditions.checkArgument(properties.length > 0, "If you add properties, properties length must be more of 0");
        this.properties.addAll(Arrays.asList(properties));
        return this;
    }

    public BlockStateGenerator excludeProperties(IProperty<?>... properties) {
//        Preconditions.checkArgument(properties.length > 0, "If you exclude properties, properties length must be more of 0");
        excludedProperties.addAll(Arrays.asList(properties));
        return this;
    }

    public BlockStateGenerator addFeatures(PropertyFeature<?>... types) {
        Arrays.stream(types).forEach(this::addFeature);
        return this;
    }

    protected void addFeature(PropertyFeature<?> feature) {
        if (featureWithItemBlock == null) featureWithItemBlock = feature;
        Preconditions.checkArgument(!feature.hasItemBlock(),
                "BlockState can't contains two feature with custom ItemBlock. " +
                        "Exist feature: " + featureWithItemBlock + "; New feature: " + feature);
        features.add(feature);
    }

    public MetadataContainer buildContainer() {
        return new MetadataContainer(block, featureWithItemBlock, features, properties, excludedProperties);
    }
}