package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ItemArmor;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderDiamond extends ItemNamed implements MaterialProvider {
    private final int color;
    private final ToolMaterial material;

    public EnderDiamond(String name, int color, ToolMaterial material) {
        super(name + "_diamond", ImmutableMap.of(EM.ID + ":items/diamonds/" + name + "_diamond", 0xffffffff));
        this.color = color;
        this.material = material;
    }

    @Override
    public ToolMaterial material() {
        return material;
    }

    @Override
    public ItemArmor.ArmorMaterial armorMaterial() {
        return null;
    }

    @Override
    public int color() {
        return color;
    }
}