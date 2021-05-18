package ru.mousecray.endmagic.items;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.EM;

import java.util.Map;

public class ShiningQuartz extends Item implements ItemTextured {
    @Override
    public Map<String, Integer> textures() {
        return ImmutableMap.of(EM.ID + ":items/shining_quartz", 0x3cffffff);
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
