package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.items.ItemTextured;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TexturedModel implements IBakedModel {
    private IBakedModel baseModel;

    public TexturedModel(IBakedModel baseModel) {
        this.baseModel = baseModel;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        Matrix4f matrix4f = baseModel.handlePerspective(cameraTransformType).getRight();
        return Pair.of(this, matrix4f);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return baseModel.getQuads(state, side, rand);
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
        return TexturedItemOverrideList.instance;
    }

    public static class TexturedItemOverrideList extends ItemOverrideList {
        public static TexturedItemOverrideList instance = new TexturedItemOverrideList();

        public TexturedItemOverrideList() {
            super(Collections.emptyList());
        }

        HashMap<Map<String, Integer>, TexturedFinalisedModel> memoization = new HashMap<>();

        private IBakedModel model(IBakedModel originalModel, ItemStack stack) {
            Item item = stack.getItem();
            if (item instanceof ItemTextured) {
                Map<String, Integer> key = ((ItemTextured) item).textures();
                return memoization.computeIfAbsent(key, (__) -> new TexturedFinalisedModel(originalModel, key));
            } else return originalModel;
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
            return model(originalModel, stack);
        }
    }
}
