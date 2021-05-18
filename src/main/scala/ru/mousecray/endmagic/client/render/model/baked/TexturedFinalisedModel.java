package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.CurrentTextureLens;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ItemLayerModel;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.items.ItemTextured;
import ru.mousecray.endmagic.util.render.RenderUtils;
import ru.mousecray.endmagic.util.render.elix_x.baked.UnpackedBakedQuad;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TexturedFinalisedModel extends BakedModelDelegate {
    private final ItemTextured item;
    public static ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    public TexturedFinalisedModel(IBakedModel baseModel, ItemTextured item) {
        super(baseModel);
        this.item = item;
    }

    private ImmutableList<Pair<TextureAtlasSprite, Integer>> getTextureAtlasSprite() {
        return item.textures()
                .entrySet()
                .stream()
                .map(e -> Pair.of(
                        RenderUtils.getAtlas(e.getKey()),
                        e.getValue()))
                .collect(ImmutableList.toImmutableList());

    }

    private static final double decalConstant = 0.0015;

    private ImmutableList<BakedQuad> getQuads() {
        if (quads == null) {
            List<Pair<TextureAtlasSprite, Integer>> textures = getTextureAtlasSprite();
            quads =
                    (item.glintTexturre().isPresent() ? buildQuadsWithoutOffset(textures) : buildQuadsWithIndexOffset(textures))
                            .flatMap(i -> i)
                            .collect(ImmutableList.toImmutableList());

            glintQuads =
                    item.glintTexturre().map(texture -> getBakedQuadsForTexture(0, RenderUtils.getAtlas(texture), 0xffffffff)
                            .collect(ImmutableList.toImmutableList()));
        }
        return glintQuads.filter(__ -> isNowRenderGlint())
                .orElse(quads);
    }

    private Stream<Stream<BakedQuad>> buildQuadsWithoutOffset(List<Pair<TextureAtlasSprite, Integer>> textures) {
        return textures.stream().map(p -> getBakedQuadsForTexture(0, p.getLeft(), p.getRight()));
    }

    private Stream<Stream<BakedQuad>> buildQuadsWithIndexOffset(List<Pair<TextureAtlasSprite, Integer>> textures) {
        return Streams.mapWithIndex(textures.stream(), (p, index) -> getBakedQuadsForTexture(index, p.getLeft(), p.getRight()));
    }

    private boolean isNowRenderGlint() {
        return CurrentTextureLens.get() == RenderUtils.getGlTextureId(RES_ITEM_GLINT);
    }

    private Stream<BakedQuad> getBakedQuadsForTexture(long index, TextureAtlasSprite atlasSprite, Integer color) {
        return ItemLayerModel.getQuadsForSprite(1, atlasSprite, DefaultVertexFormats.ITEM, Optional.empty())
                .stream()
                .map(UnpackedBakedQuad::unpack)
                .peek(quad -> quad.getVertices()
                        .forEach(v -> {
                            v.setColor(RGBA.fromARGB(color));
                            if (index != 0)
                                v.setPos(v.getPos().scale(1 + index * decalConstant).addVector(0, 0, index * -decalConstant / 2));
                        }))
                .map(quad -> quad.pack(DefaultVertexFormats.ITEM));
    }

    private ImmutableList<BakedQuad> quads;
    private Optional<ImmutableList<BakedQuad>> glintQuads;

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return getQuads();
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
    public ItemOverrideList getOverrides() {
        throw new UnsupportedOperationException("TexturedFinalisedModel#getOverrides");
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return Pair.of(this, item.handlePerspective(this, cameraTransformType));
    }
}
