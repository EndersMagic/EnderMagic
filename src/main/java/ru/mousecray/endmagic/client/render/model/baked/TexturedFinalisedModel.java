package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Map;

public class TexturedFinalisedModel implements IBakedModel {
    private IBakedModel baseModel;
    private Map<String, Integer> parts;

    public TexturedFinalisedModel(IBakedModel baseModel, Map<String, Integer> key) {
        this.baseModel = baseModel;
        parts = key;
    }

    private ImmutableList<Pair<TextureAtlasSprite, Integer>> getTextureAtlasSprite() {
        return parts
                .entrySet()
                .stream()
                .map(e -> Pair.of(
                        Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(e.getKey()),
                        e.getValue()))
                .collect(ImmutableList.toImmutableList());

    }

    private ImmutableList<BakedQuad> getQuads() {
        if (quads == null) {
            quads = getTextureAtlasSprite()
                    .stream()
                    .flatMap(p ->
                            net.minecraftforge.client.model.ItemTextureQuadConverter.convertTexture(
                                    DefaultVertexFormats.ITEM,
                                    TRSRTransformation.identity(),
                                    p.getKey(), p.getKey(),
                                    0, null,
                                    p.getValue(),
                                    -1)
                                    .stream())
                    .collect(ImmutableList.toImmutableList());
        }
        return quads;
    }

    private ImmutableList<BakedQuad> quads;

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return getQuads();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        Matrix4f matrix4f = baseModel.handlePerspective(cameraTransformType).getRight();
        Matrix4f m = new Matrix4f();
        m.setIdentity();
        return Pair.of(this, matrix4f == null ? m : matrix4f);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return baseModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return baseModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        throw new UnsupportedOperationException("TexturedFinalisedModel#getOverrides");
    }
}
