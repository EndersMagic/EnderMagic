package ru.mousecray.endmagic.items;

import net.minecraft.item.Item;
import ru.mousecray.endmagic.EndMagicData;

public class Default extends Item {
	
	public Default(String name) {
		this(name, 64);
	}
	
	public Default(String name, int size) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(size);
		setCreativeTab(EndMagicData.EM_CREATIVE);
	}
}
