package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import scala.Function4;

import javax.annotation.Nullable;
import java.util.Collections;

public class GenericItemOverrideList {
    public static ItemOverrideList fromLambda(Function4<IBakedModel, ItemStack, World, EntityLivingBase, IBakedModel> handleItemState) {
        return new ItemOverrideList(Collections.emptyList()) {
            @Override
            public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                return handleItemState.apply(originalModel, stack, world, entity);
            }
        };
    }
}
