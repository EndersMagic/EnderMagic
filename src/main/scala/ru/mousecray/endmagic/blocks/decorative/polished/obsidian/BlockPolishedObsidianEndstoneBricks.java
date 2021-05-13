package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.SeparatedRenderLayersBakedModel;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;

public class BlockPolishedObsidianEndstoneBricks extends BlockPolishedObsidian implements IExtendedProperties {

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        modelRegistration.addBakedModelOverride(getRegistryName(), SeparatedRenderLayersBakedModel.apply(ImmutableMap.of(
                BlockRenderLayer.SOLID, new ResourceLocation(EM.ID, "models/block/block_polished_obsidian_endstone_bricks_solid"),
                BlockRenderLayer.TRANSLUCENT, new ResourceLocation(EM.ID, "models/block/block_polished_obsidian_endstone_bricks_translucent")
        ), BlockRenderLayer.SOLID));
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID;
    }

}
