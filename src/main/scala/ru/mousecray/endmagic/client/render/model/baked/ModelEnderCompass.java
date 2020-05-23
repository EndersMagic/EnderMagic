package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import ru.mousecray.endmagic.gameobj.items.EnderCompass;

import java.util.HashMap;
import java.util.Map;

public class ModelEnderCompass extends BakedModelDelegate {

    private static Map<Integer, FinalisedModelEnderCompass> modelCache = new HashMap<>();

    public ModelEnderCompass(IBakedModel base) {
        super(base, GenericItemOverrideList.fromLambda(
                (originalModel, stack, world, entity) -> modelCache.computeIfAbsent(EnderCompass.getCompassKey(stack), key ->
                        new FinalisedModelEnderCompass(originalModel, stack, entity == null ? Minecraft.getMinecraft().player : entity))));
    }
}
