package ru.mousecray.endmagic.items.materials;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class EnderSteel extends BaseMaterial implements MaterialProvider {
    private final ToolMaterial material;
    private final ItemArmor.ArmorMaterial armorMaterial;
    private final ItemArmor.ArmorMaterial chainmailMaterial;

    public EnderSteel(String name, int color, ToolMaterial material, ItemArmor.ArmorMaterial armorMaterial) {
        super(name, "_steel", color);
        this.material = material;
        this.armorMaterial = armorMaterial;
        chainmailMaterial = constructChainmailMaterial(armorMaterial);
    }

    private ItemArmor.ArmorMaterial constructChainmailMaterial(ItemArmor.ArmorMaterial armorMaterial) {
        int[] reductionAmounts = {armorMaterial.getDamageReductionAmount(FEET),
                armorMaterial.getDamageReductionAmount(LEGS),
                armorMaterial.getDamageReductionAmount(CHEST),
                armorMaterial.getDamageReductionAmount(HEAD)};

        double chainmail_durability_factor = 0.9;

        int helmetDerabilityFactor = 11;//ItemArmor#MAX_DAMAGE_ARRAY[HEAD.getIndex()]
        int durability = armorMaterial.getDurability(HEAD) / helmetDerabilityFactor;

        return EnumHelper.addArmorMaterial(
                armorMaterial.name(),
                armorMaterial.getName() + "_chainmail",
                (int) (durability * chainmail_durability_factor),
                reductionAmounts,
                armorMaterial.getEnchantability(),
                armorMaterial.getSoundEvent(),
                armorMaterial.getToughness());
    }

    @Override
    public ToolMaterial material() {
        return material;
    }


    @Override
    public ItemArmor.ArmorMaterial armorMaterial() {
        return armorMaterial;
    }

    @Override
    public ItemArmor.ArmorMaterial chainmailMaterial() {
        return chainmailMaterial;
    }
}