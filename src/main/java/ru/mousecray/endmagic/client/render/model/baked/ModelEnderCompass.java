package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;

public class ModelEnderCompass extends BakedModelDelegate {

    public ModelEnderCompass(IBakedModel base) {
        super(base, GenericItemOverrideList.fromLambda(FinalisedModelEnderCompass::new));
    }
}
