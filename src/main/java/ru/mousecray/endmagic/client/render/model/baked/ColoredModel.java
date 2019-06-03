package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.elix_x.baked.UnpackedBakedQuad;
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ColoredModel extends BakedModelDelegate {
    private final RGBA color;
    private ItemOverrideList overrides;

    public ColoredModel(IBakedModel base, RGBA color, boolean isFinalised) {
        super(base);
        this.color = color;
        if (isFinalised)
            overrides = super.getOverrides();
        else
            overrides = new ItemOverrideList(Collections.emptyList()) {
                private IBakedModel finalisedModel = new ColoredModel(base, color, true){
                    @Override
                    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
                        System.out.println("test");
                        return super.getQuads(state, side, rand);
                    }
                };

                @Override
                public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                    return finalisedModel;
                }
            };
    }

    public static Function<IBakedModel, IBakedModel> of(RGBA color) {
        return base -> new ColoredModel(base, color, false);
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

    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }
}
