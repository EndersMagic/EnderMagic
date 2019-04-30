package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.mousecray.endmagic.items.ItemNamed;

import javax.annotation.Nullable;
import java.util.HashMap;

public class TexturedModel extends BakedModelDelegate {

    static HashMap<String, TexturedFinalisedModel> memoization = new HashMap<>();

    public TexturedModel(IBakedModel baseModel) {
        super(baseModel, GenericItemOverrideList.fromLambda((IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) -> {
            Item item = stack.getItem();
            if (item instanceof ItemNamed) {
                String key = ((ItemNamed) item).textureName();
                return memoization.computeIfAbsent(key, (__) -> new TexturedFinalisedModel(originalModel, key));
            } else return originalModel;
        }));
    }
}
