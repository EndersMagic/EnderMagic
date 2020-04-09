package ru.mousecray.endmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.ColoredModel;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

public interface BlockColored extends IExtendedProperties {

    @Override
    default void registerModels(IModelRegistration modelRegistration) {
        Block block = (Block) this;
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(block.getRegistryName(), "inventory"));
        modelRegistration.
                addBakedModelOverride(block.getRegistryName(), ColoredModel.of(color()));
    }

    RGBA color();
}
