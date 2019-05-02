package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.mousecray.endmagic.items.EnderCompass;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelEnderCompass extends BakedModelDelegate {

    public ModelEnderCompass(IBakedModel base) {
        super(base);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideListEnderCompass.instance;
    }

    private static class ItemOverrideListEnderCompass extends ItemOverrideList {
        private Map<String, FinalisedModelEnderCompass> modelCache = new HashMap<>();

        public static ItemOverrideListEnderCompass instance = new ItemOverrideListEnderCompass();

        public ItemOverrideListEnderCompass() {
            super(Collections.emptyList());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
            return modelCache.computeIfAbsent(EnderCompass.getCompassKey(stack), key ->
                    new FinalisedModelEnderCompass(originalModel, stack, entity == null ? Minecraft.getMinecraft().player : entity));
        }
    }
}
