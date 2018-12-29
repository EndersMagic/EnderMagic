package ru.mousecray.endmagic.items;

import net.minecraft.item.ItemSword;
import ru.mousecray.endmagic.EndMagicData;

public class ImmortalSword extends ItemSword {
	
	public ImmortalSword(ToolMaterial material) {
		super(material);
		setRegistryName("immortal_sword");
		setUnlocalizedName("immortal_sword");
		setCreativeTab(EndMagicData.EM_CREATIVE);
	}
}
