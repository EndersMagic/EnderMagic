package ru.mousecray.endmagic.client.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.util.Vec2i;

public interface IStructuralGuiElement {
    default Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    default int width() {
        return 0;
    }

    default int height() {
        return 0;
    }

    default Vec2i fixPoint() {
        return new Vec2i(0, 0);
    }

    void render(int mouseX, int mouseY);

    default <A> A cycleElementOf(ImmutableList<A> list, A defaultValue) {
        if ((list.size() > 0))
            return list.get((int) (System.currentTimeMillis() / 1000L % list.size()));
        else
            return defaultValue;
    }
}
