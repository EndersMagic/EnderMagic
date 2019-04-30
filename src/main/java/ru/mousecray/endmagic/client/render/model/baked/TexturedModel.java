package ru.mousecray.endmagic.client.render.model.baked;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.mousecray.endmagic.items.ItemNamed;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;

public class TexturedModel extends BakedModelDelegate {

    public TexturedModel(IBakedModel baseModel) {
        super(baseModel);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return TexturedItemOverrideList.instance;
    }

    public static class TexturedItemOverrideList extends ItemOverrideList {
        public static TexturedItemOverrideList instance = new TexturedItemOverrideList();

        public TexturedItemOverrideList() {
            super(Collections.emptyList());
        }

        HashMap<String, TexturedFinalisedModel> memoization = new HashMap<>();

        private IBakedModel model(IBakedModel originalModel, ItemStack stack) {
            Item item = stack.getItem();
            if (item instanceof ItemNamed) {
                String key = ((ItemNamed) item).textureName();
                return memoization.computeIfAbsent(key, (__) -> new TexturedFinalisedModel(originalModel, key));
            } else return originalModel;
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
            return model(originalModel, stack);
        }
    }
}
