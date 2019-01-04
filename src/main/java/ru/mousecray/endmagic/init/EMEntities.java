package ru.mousecray.endmagic.init;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import ru.mousecray.endmagic.entity.*;

public class EMEntities {
	public static EntityEntry purpleEnderPearl = EntityEntryBuilder
			.create()
			.entity(EntityPurplePearl.class)
			.name("Purple Ender Pearl")
			.id("purple_ender_pearl", 0)
			.tracker(64, 20, true)
			.build();
	public static EntityEntry blueEnderPearl = EntityEntryBuilder
			.create()
			.entity(EntityBluePearl.class)
			.name("Blue Ender Pearl")
			.id("blue_ender_pearl", 1)
			.tracker(64, 20, true)
			.build();
}