package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;

public class ModelEnderCompass extends BakedModelDelegate {

    public ModelEnderCompass(IBakedModel base) {
        super(base);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideListCompass.instance;
    }

    public static class ItemOverrideListCompass extends ItemOverrideList {
        public static ItemOverrideListCompass instance = new ItemOverrideListCompass();

        public ItemOverrideListCompass() {
            super(Collections.emptyList());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
            return new FinalisedModelEnderCompass(originalModel, stack, world, entity);
        }
    }
}
