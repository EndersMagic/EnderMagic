package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import ru.mousecray.endmagic.util.render.elix_x.baked.UnpackedBakedQuad;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class ColoredModel extends BakedModelDelegate {
    private final RGBA color;

    public ColoredModel(IBakedModel base, RGBA color) {
        super(base);
        this.color = color;
    }

    public static Function<IBakedModel, IBakedModel> of(RGBA color) {
        return base -> new ColoredModel(base, color);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return super.getQuads(state, side, rand)
                .stream()
                .map(UnpackedBakedQuad::unpack)
                .peek(q -> q.getVertices().forEach(v -> v.setColor(color)))
                .map(q -> q.pack(DefaultVertexFormats.BLOCK))
                .collect(ImmutableList.toImmutableList());
    }
}
