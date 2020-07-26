package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ItemArmor;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderSteel extends ItemNamed implements MaterialProvider {
    private final ToolMaterial material;
    private final ItemArmor.ArmorMaterial armorMaterial;

    public EnderSteel(String name, int color, ToolMaterial material, ItemArmor.ArmorMaterial armorMaterial) {
        super(name+"_steel", ImmutableMap.of(EM.ID + ":items/colorless_steel", color | 0xff000000));
        this.material = material;
        this.armorMaterial = armorMaterial;
    }

    @Override
    public ToolMaterial material() {
        return material;
    }


    @Override
    public ItemArmor.ArmorMaterial armorMaterial() {
        return armorMaterial;
    }
}