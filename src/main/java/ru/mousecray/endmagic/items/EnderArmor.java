package ru.mousecray.endmagic.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import ru.mousecray.endmagic.EndMagicData;

public class EnderArmor extends ItemArmor {

	public EnderArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name) {
		super(material, renderIndex, equipmentSlot);
        setRegistryName(name);
        setUnlocalizedName(name);
		setCreativeTab(EndMagicData.EM_CREATIVE);
	}
}
