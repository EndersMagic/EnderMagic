package ru.mousecray.endmagic.gameobj.items.materials;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ItemArmor;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.gameobj.items.ItemNamed;

public class EnderDiamond extends ItemNamed implements MaterialProvider {
    private final ToolMaterial material;

    public EnderDiamond(String name, int color, ToolMaterial material) {
        super(name, ImmutableMap.of(EM.ID + ":items/colorless_diamond", color | 0xff000000));
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
}