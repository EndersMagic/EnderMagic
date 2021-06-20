package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.BlockRenderLayer;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.SeparatedRenderLayersBakedModel;
import ru.mousecray.endmagic.client.render.model.baked.TranslucentPartsModel;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;
import ru.mousecray.endmagic.util.render.RenderUtils;

public class BlockPolishedObsidianEndstoneBricks extends BlockPolishedObsidian implements IExtendedProperties {

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        overrideModelForVariant(modelRegistration, "normal");
        overrideModelForVariant(modelRegistration, "render_side_parts=default");
    }

    private void overrideModelForVariant(IModelRegistration modelRegistration, String normal) {
        modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), normal),
                __ -> new SeparatedRenderLayersBakedModel(ImmutableMap.of(
                        BlockRenderLayer.SOLID, RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/solid"),
                        BlockRenderLayer.TRANSLUCENT, new TranslucentPartsModel(RenderUtils.loadEMJsonModel("models/block/polished_obsidian/bricks/translucent"))
                ), BlockRenderLayer.SOLID));
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID;
    }
}
