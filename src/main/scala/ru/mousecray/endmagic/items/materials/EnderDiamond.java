package ru.mousecray.endmagic.items.materials;

import net.minecraft.item.ItemArmor;

public class EnderDiamond extends BaseMaterial implements MaterialProvider {
    private final ToolMaterial material;

    public EnderDiamond(String name, int color, ToolMaterial material) {
        super(name, "_diamond", color);
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
    public ItemArmor.ArmorMaterial chainmailMaterial() {
        return null;
    }
}