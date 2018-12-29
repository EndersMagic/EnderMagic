package ru.mousecray.endmagic.items;

import net.minecraft.item.ItemPickaxe;
import ru.mousecray.endmagic.EndMagicData;

public class VanishingPickaxe extends ItemPickaxe {
	
    public VanishingPickaxe(ToolMaterial material) {
        super(material);
        setRegistryName("vanishing_pickaxe");
        setUnlocalizedName("vanishing_pickaxe");
		setCreativeTab(EndMagicData.EM_CREATIVE);
    }
}