package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.EnumHelper;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.render.RenderUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class SeparatedRenderLayersBakedModel extends BakedModelDelegate {
    private final Map<BlockRenderLayer, IBakedModel> parts;

    public SeparatedRenderLayersBakedModel(Map<BlockRenderLayer, IBakedModel> parts, BlockRenderLayer primaryPart) {
        super(parts.get(primaryPart));
        this.parts = parts;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        IBakedModel iBakedModel = parts.get(MinecraftForgeClient.getRenderLayer());
        return iBakedModel == null ? ImmutableList.of() : iBakedModel.getQuads(state, side, rand);
    }

    public static Function<IBakedModel, IBakedModel> apply(Map<BlockRenderLayer, String> parts, BlockRenderLayer primaryPart) {
        return __ -> new SeparatedRenderLayersBakedModel(parts.entrySet().stream().collect(toMap(e -> e.getKey(), e -> RenderUtils.loadJsonModel(new ResourceLocation(EM.ID, e.getValue())))), primaryPart);
    }

    public static void main(String[] args){

        System.out.println(Arrays.toString(Test.values()));
        EnumHelper.addEnum(Test.class,"D",new Class[0]);
        System.out.println(Arrays.toString(Test.values()));
        System.out.println(Test.valueOf("D"));
    }
    public static enum Test{
        A,B,C
    }
}
