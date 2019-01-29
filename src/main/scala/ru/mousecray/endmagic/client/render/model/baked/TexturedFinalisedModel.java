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
import net.minecraftforge.client.model.ItemLayerModel;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TexturedFinalisedModel implements IBakedModel {
    private IBakedModel baseModel;
    private String key;

    public TexturedFinalisedModel(IBakedModel baseModel, String key) {
        this.baseModel = baseModel;
        this.key = key;
    }

    private TextureAtlasSprite getTextureAtlasSprite() {
        if (textureAtlasSprite == null) {
            textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(key);
        }
        return textureAtlasSprite;
    }

    private ImmutableList<BakedQuad> getQuads() {
        if (quads == null) {
            quads = ItemLayerModel.getQuadsForSprite(0, getTextureAtlasSprite(), DefaultVertexFormats.ITEM, Optional.empty());
        }
        return quads;
    }

    private TextureAtlasSprite textureAtlasSprite;
    private ImmutableList<BakedQuad> quads;

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side != null)
            return Collections.emptyList();
        else
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
