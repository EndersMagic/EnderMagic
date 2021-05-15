package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSidePartsHolder.RenderSideParts;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSidePartsHolder.RenderSideParts.FaceVisibility.invisible_bottom;
import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSidePartsHolder.RenderSideParts.FaceVisibility.invisible_top;

public class TranslucentPartsModel extends BakedModelDelegate {
    public TranslucentPartsModel(IBakedModel base) {
        super(base);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        List<BakedQuad> quads = super.getQuads(state, side, rand);
        if (state != null) {
            RenderSideParts renderSideParts = state.getValue(RenderSideParts.PROPERTY);
            return quads.stream().flatMap(q -> {
                if (q instanceof MarkedBakedQuad) {
                    String side_part = ((MarkedBakedQuad) q).customValues.getOrDefault("side_part", "").toString();
                    if (side_part.isEmpty() || canRenderQuad(q, side_part, renderSideParts))
                        return Stream.of(q);
                    else
                        return Stream.empty();

                } else
                    return Stream.of(q);
            }).collect(Collectors.toList());
        } else
            return quads;
    }

    private boolean canRenderQuad(BakedQuad q, String side_part, RenderSideParts renderSideParts) {
        switch (q.getFace()) {
            case DOWN:
                return renderSideParts.down.toString().equals(side_part);
            case UP:
                return renderSideParts.up.toString().equals(side_part);
            case NORTH:
                return isSidePartRelevant(side_part, renderSideParts.north);
            case SOUTH:
                return isSidePartRelevant(side_part, renderSideParts.south);
            case WEST:
                return isSidePartRelevant(side_part, renderSideParts.west);
            case EAST:
                return isSidePartRelevant(side_part, renderSideParts.east);
            default:
                return true;
        }
    }

    private boolean isSidePartRelevant(String side_part, RenderSideParts.FaceVisibility actualVisibility) {
        switch (actualVisibility) {
            case visible_all:
                return true;
            case invisible_top:
                return side_part.equals(invisible_top.toString());
            case invisible_bottom:
                return side_part.equals(invisible_bottom.toString());
            case invisible_all:
                return false;
            default:
                return true;
        }
    }
}
