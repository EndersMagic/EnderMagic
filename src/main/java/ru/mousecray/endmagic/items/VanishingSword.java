package ru.mousecray.endmagic.items;

import net.minecraft.item.ItemSword;
import ru.mousecray.endmagic.EndMagicData;

public class VanishingSword extends ItemSword {
	
	public VanishingSword(ToolMaterial material) {
        super(material);
        setRegistryName("vanishing_sword");
        setUnlocalizedName("vanishing_sword");
		setCreativeTab(EndMagicData.EM_CREATIVE);
    }
}
