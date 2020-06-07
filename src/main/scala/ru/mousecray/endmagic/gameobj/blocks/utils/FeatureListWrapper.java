package ru.mousecray.endmagic.gameobj.blocks.utils;

import java.util.List;

public class FeatureListWrapper {
    public final PropertyFeature feature;
    public final List<FeatureTypes> availableFeatures;

    public FeatureListWrapper(PropertyFeature feature, List<FeatureTypes> availableFeatures) {
        this.feature = feature;
        this.availableFeatures = availableFeatures;
    }
}
