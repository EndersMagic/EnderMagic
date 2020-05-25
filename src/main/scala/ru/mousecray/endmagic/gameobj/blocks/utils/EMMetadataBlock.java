package ru.mousecray.endmagic.gameobj.blocks.utils;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nullable;

public abstract class EMMetadataBlock extends MetadataBlock implements ITechnicalBlock {
    public EMMetadataBlock(Material material) {
        super(material);
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        getMetadataContainer().registerItemModels(this);
    }

    @Nullable @Override
    public CreativeTabs getCustomCreativeTab() {
        return EM.EM_CREATIVE;
    }
}