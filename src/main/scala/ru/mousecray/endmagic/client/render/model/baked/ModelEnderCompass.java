package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.gameobj.items.EnderCompass;

import java.util.HashMap;
import java.util.Map;

public class ModelEnderCompass extends BakedModelDelegate {

    private static Map<Integer, FinalisedModelEnderCompass> modelCache = new HashMap<>();

    public ModelEnderCompass(IBakedModel base) {
        super(base, GenericItemOverrideList.fromLambda(
                (originalModel, stack, world, entity) ->
                {
                    if (entity==null || entity.isSneaking())
                        return modelCache.computeIfAbsent(EnderCompass.getCompassKey(stack), key ->
                                new FinalisedModelEnderCompass(originalModel, stack, entity == null ? Minecraft.getMinecraft().player : entity));
                    else
                        return Minecraft.getMinecraft().getRenderItem()
                                .getItemModelWithOverrides(new ItemStack(Items.APPLE), Minecraft.getMinecraft().world, null);
                }));
    }
}
