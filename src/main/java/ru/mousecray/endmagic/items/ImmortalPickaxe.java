package ru.mousecray.endmagic.items;

import net.minecraft.item.ItemPickaxe;
import ru.mousecray.endmagic.EndMagicData;

public class ImmortalPickaxe extends ItemPickaxe {
	
    public ImmortalPickaxe(ToolMaterial material) {
        super(material);
        setRegistryName("immortal_pickaxe");
        setUnlocalizedName("immortal_pickaxe");
		setCreativeTab(EndMagicData.EM_CREATIVE);
    }
}