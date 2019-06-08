package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

public class BakedModelDelegate implements IBakedModel {

    protected final IBakedModel base;
    private final ItemOverrideList itemOverrideList;

    public BakedModelDelegate(IBakedModel base) {
        this.base = base;
        itemOverrideList = base.getOverrides();
    }


    public BakedModelDelegate(IBakedModel base, ItemOverrideList itemOverrideList) {
        this.base = base;
        this.itemOverrideList = itemOverrideList;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return base.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return base.isAmbientOcclusion();
    }

    @Override
    public boolean isAmbientOcclusion(IBlockState state) {
        return base.isAmbientOcclusion(state);
    }

    @Override
    public boolean isGui3d() {
        return base.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return base.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return base.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return itemOverrideList;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return base.getItemCameraTransforms();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        Matrix4f matrix4f = base.handlePerspective(cameraTransformType).getRight();
        return Pair.of(this, matrix4f);
    }
}