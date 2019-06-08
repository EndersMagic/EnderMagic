package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;

public class GenericItemOverrideList {
    public static ItemOverrideList fromLambda(HandleItemState handleItemState) {
        return new ItemOverrideList(Collections.emptyList()) {
            @Override
            public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                return handleItemState.apply(originalModel, stack, world, entity);
            }
        };
    }

    public static interface HandleItemState {
        public IBakedModel apply(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity);
    }
}
