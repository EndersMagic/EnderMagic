package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
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
import ru.mousecray.endmagic.items.ItemTextured;
import ru.mousecray.endmagic.util.render.elix_x.baked.UnpackedBakedQuad;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Optional;

public class TexturedFinalisedModel extends BakedModelDelegate {
    private final ItemTextured item;

    public TexturedFinalisedModel(IBakedModel baseModel, ItemTextured item) {
        super(baseModel);
        this.item = item;
    }

    private ImmutableList<Pair<TextureAtlasSprite, Integer>> getTextureAtlasSprite() {
        return item.textures()
                .entrySet()
                .stream()
                .map(e -> Pair.of(
                        Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(e.getKey()),
                        e.getValue()))
                .collect(ImmutableList.toImmutableList());

    }

    private ImmutableList<BakedQuad> getQuads() {
        if (quads==null) {
            final double decalConstant = 0.0015;
            quads =
                    Streams.mapWithIndex(getTextureAtlasSprite().stream(), (p, index) ->
                            ItemLayerModel.getQuadsForSprite(1, p.getKey(), DefaultVertexFormats.ITEM, Optional.empty())
                                    .stream()
                                    .map(UnpackedBakedQuad::unpack)
                                    .peek(quad -> quad.getVertices()
                                            .forEach(v -> {
                                                v.setColor(RGBA.fromARGB(p.getValue()));
                                                v.setPos(v.getPos().scale(1 + index * decalConstant).addVector(0, 0, index * -decalConstant / 2));
                                            }))
                                    .map(quad -> quad.pack(DefaultVertexFormats.ITEM))
                    )
                            .flatMap(i -> i)
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
