package ru.mousecray.endmagic.client.render;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Vector3f;
import ru.mousecray.endmagic.client.render.model.baked.MarkedBakedQuad;

public class MarkingFaceBakery extends FaceBakery {
    public BakedQuad makeBakedQuad(Vector3f posFrom, Vector3f posTo, BlockPartFace face, TextureAtlasSprite sprite, EnumFacing facing, net.minecraftforge.common.model.ITransformation modelRotationIn, BlockPartRotation partRotation, boolean uvLocked, boolean shade) {
        BakedQuad vanillaBakedQuad = super.makeBakedQuad(posFrom, posTo, face, sprite, facing, modelRotationIn, partRotation, uvLocked, shade);
        if (face instanceof CustomPropertiesBlockPartFaceDeserializer.CustomBlockPartFace)
            return new MarkedBakedQuad(vanillaBakedQuad, ((CustomPropertiesBlockPartFaceDeserializer.CustomBlockPartFace) face).customValues);
        else
            return vanillaBakedQuad;
    }
}
