package ru.mousecray.endmagic.init;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import ru.mousecray.endmagic.entity.*;

public class EMEntities {
	public static EntityEntry emEnderPearl = EntityEntryBuilder
			.create()
			.entity(EntityEMEnderPearl.class)
			.name("EMEnderPearl")
			.id("em_ender_pearl", 0)
			.tracker(64, 20, true)
			.build();
	public static EntityEntry enderArrow = EntityEntryBuilder
			.create()
			.entity(EntityEnderArrow.class)
			.name("EnderArrow")
			.id("ender_arrow", 1)
			.tracker(64, 20, true)
			.build();
	public static EntityEntry entityCurseBush = EntityEntryBuilder
			.create()
			.entity(EntityCurseBush.class)
			.name("CurseBush")
			.id("curse_bush", 2)
			.tracker(32, 1, true)
			.build();
}