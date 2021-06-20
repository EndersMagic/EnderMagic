package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;

import java.util.Map;

public class MarkedBakedQuad extends BakedQuad {
    public final Map<String, Object> customValues;

    public MarkedBakedQuad(BakedQuad vanillaBakedQuad, Map<String, Object> customValues) {
        super(vanillaBakedQuad.getVertexData(), vanillaBakedQuad.getTintIndex(), vanillaBakedQuad.getFace(), vanillaBakedQuad.getSprite(), vanillaBakedQuad.shouldApplyDiffuseLighting(), vanillaBakedQuad.getFormat());
        this.customValues = customValues;
    }
}
