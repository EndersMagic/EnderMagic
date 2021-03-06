package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.common.property.IExtendedBlockState;
import ru.mousecray.endmagic.util.render.RenderUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BakedModelFullbright extends BakedModelDelegate {

    private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>() {
        @Override
        public List<BakedQuad> load(CacheKey key) {
            return transformQuads(key.base.getQuads(key.state, key.side, 0), key.textures);
        }
    });

    private Set<String> textures;
    private boolean cacheDisabled = false;

    public BakedModelFullbright(IBakedModel base, String... textures) {
        super(base);

        this.textures = new HashSet<>(Arrays.asList(textures));
    }

    public BakedModelFullbright setCacheDisabled() {
        cacheDisabled = true;

        return this;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (state == null) return base.getQuads(state, side, rand);

        if (cacheDisabled) return transformQuads(base.getQuads(state, side, 0), textures);

        return CACHE.getUnchecked(new CacheKey(base, textures, state instanceof IExtendedBlockState ? ((IExtendedBlockState) state).getClean() : state, side));
    }

    private static List<BakedQuad> transformQuads(List<BakedQuad> oldQuads, Set<String> textures) {
        List<BakedQuad> quads = new ArrayList<>(oldQuads);

        for (int i = 0; i < quads.size(); ++i) {
            BakedQuad quad = quads.get(i);

            if (textures.contains(quad.getSprite().getIconName())) quads.set(i, transformQuad(quad, 0.007F));
        }

        return quads;
    }

    private static BakedQuad transformQuad(BakedQuad quad, float light) {
        if (RenderUtils.isLightMapDisabled()) return quad;

        VertexFormat newFormat = RenderUtils.getFormatWithLightMap(quad.getFormat());

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(newFormat);

        VertexLighterFlat trans = new VertexLighterFlat(Minecraft.getMinecraft().getBlockColors()) {
            @Override
            protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z) {
                lightmap[0] = light;
                lightmap[1] = light;
            }

            @Override
            public void setQuadTint(int tint) {}
        };

        trans.setParent(builder);

        quad.pipe(trans);

        builder.setQuadTint(quad.getTintIndex());
        builder.setQuadOrientation(quad.getFace());
        builder.setTexture(quad.getSprite());
        builder.setApplyDiffuseLighting(false);

        return builder.build();
    }

    static class CacheKey {
        IBakedModel base;
        Set<String> textures;
        IBlockState state;
        EnumFacing side;

        public CacheKey(IBakedModel base, Set<String> textures, IBlockState state, EnumFacing side) {
            this.base = base;
            this.textures = textures;
            this.state = state;
            this.side = side;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            if (cacheKey.side != side) return false;
            if (!state.equals(cacheKey.state)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            return state.hashCode() + (31 * (side != null ? side.hashCode() : 0));
        }
    }
}
