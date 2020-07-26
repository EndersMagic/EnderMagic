package ru.mousecray.endmagic.items.materials;

import net.minecraft.item.ItemArmor;

public class EnderSteel extends BaseMaterial implements MaterialProvider {
    private final ToolMaterial material;
    private final ItemArmor.ArmorMaterial armorMaterial;

    public EnderSteel(String name, int color, ToolMaterial material, ItemArmor.ArmorMaterial armorMaterial) {
        super(name, "_steel", color);
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