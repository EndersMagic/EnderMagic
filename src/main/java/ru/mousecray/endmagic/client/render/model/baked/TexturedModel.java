package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.mousecray.endmagic.items.ItemTextured;

import java.util.HashMap;

public class TexturedModel extends BakedModelDelegate {
    private static HashMap<ItemTextured, TexturedFinalisedModel> memoization = new HashMap<>();

    private static IBakedModel model(IBakedModel originalModel, ItemStack stack, World ___, EntityLivingBase ____) {
        Item item = stack.getItem();
        if (item instanceof ItemTextured) {
            ItemTextured item1 = (ItemTextured) item;
            return memoization.computeIfAbsent(item1, (__) -> new TexturedFinalisedModel(originalModel, item1));
        } else
            return originalModel;
    }

    public TexturedModel(IBakedModel baseModel) {
        super(baseModel, GenericItemOverrideList.fromLambda(TexturedModel::model));
    }
}