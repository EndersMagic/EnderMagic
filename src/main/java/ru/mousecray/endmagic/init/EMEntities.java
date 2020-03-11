package ru.mousecray.endmagic.init;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import ru.mousecray.endmagic.entity.*;

public class EMEntities {
	private static int nextId = 0;
	public static EntityEntry emEnderPearl = EntityEntryBuilder
			.create()
			.entity(EntityEMEnderPearl.class)
			.name("EMEnderPearl")
			.id("em_ender_pearl", nextId++)
			.tracker(64, 50, true)
			.build();
	public static EntityEntry enderArrow = EntityEntryBuilder
			.create()
			.entity(EntityEnderArrow.class)
			.name("EnderArrow")
			.id("ender_arrow", nextId++)
			.tracker(64, 20, true)
			.build();
	public static EntityEntry entityCurseBush = EntityEntryBuilder
			.create()
			.entity(EntityCurseBush.class)
			.name("CurseBush")
			.id("curse_bush", nextId++)
			.tracker(32, 1, true)
			.build();
}