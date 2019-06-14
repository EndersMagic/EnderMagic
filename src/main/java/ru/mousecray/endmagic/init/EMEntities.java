package ru.mousecray.endmagic.init;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import ru.mousecray.endmagic.entity.*;

public class EMEntities {
	public static EntityEntry purpleEnderPearl = EntityEntryBuilder
			.create()
			.entity(EntityPurplePearl.class)
			.name("PurpleEnderPearl")
			.id("purple_ender_pearl", 0)
			.tracker(64, 20, true)
			.build();
	public static EntityEntry blueEnderPearl = EntityEntryBuilder
			.create()
			.entity(EntityBluePearl.class)
			.name("BlueEnderPearl")
			.id("blue_ender_pearl", 1)
			.tracker(64, 20, true)
			.build();
	public static EntityEntry enderArrow = EntityEntryBuilder
			.create()
			.entity(EntityEnderArrow.class)
			.name("EnderPearl")
			.id("ender_pearl", 2)
			.tracker(64, 20, true)
			.build();
	public static EntityEntry entityCurseBush = EntityEntryBuilder
			.create()
			.entity(EntityCurseBush.class)
			.name("CurseBush")
			.id("curse_bush", 3)
			.tracker(32, 1, true)
			.build();
}