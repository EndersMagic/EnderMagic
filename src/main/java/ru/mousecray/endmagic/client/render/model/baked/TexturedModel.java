package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.mousecray.endmagic.items.ItemTextured;

import java.util.HashMap;
import java.util.Map;

public class TexturedModel extends BakedModelDelegate {
    private static HashMap<Map<String, Integer>, TexturedFinalisedModel> memoization = new HashMap<>();

    private static IBakedModel model(IBakedModel originalModel, ItemStack stack, World ___, EntityLivingBase ____) {
        Item item = stack.getItem();
        if (item instanceof ItemTextured) {
            Map<String, Integer> key = ((ItemTextured) item).textures();
            return memoization.computeIfAbsent(key, (__) -> new TexturedFinalisedModel(originalModel, key));
        } else
            return originalModel;
    }

    public TexturedModel(IBakedModel baseModel) {
        super(baseModel, GenericItemOverrideList.fromLambda(TexturedModel::model));
    }
}