package ru.mousecray.endmagic.gameobj.items;

import net.minecraft.item.ItemFood;

public class EnderApple extends ItemFood implements ItemOneWhiteEMTextured {
    public EnderApple() {
        super(4, 0.3F, false);
    }

    @Override
    public String texture() {
        return "ender_apple";
    }

    //TODO: tooltip, effect
}
