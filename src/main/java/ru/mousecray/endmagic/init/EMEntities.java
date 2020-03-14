package ru.mousecray.endmagic.init;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import ru.mousecray.endmagic.entity.EntityEMEnderPearl;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

public class EMEntities {

	public static final EntityEntry emEnderPearl = createEntityEntry(EntityEMEnderPearl.class, "EMEnderPearl", 128, 1, true);

//	public static EntityEntry enderArrow = EntityEntryBuilder
//			.create()
//			.entity(EntityEnderArrow.class)
//			.name("EnderArrow")
//			.id("ender_arrow", nextId++)
//			.tracker(64, 20, true)
//			.build();
//	
//	@EMEntity(type = EnumEntityRegistryType.DEFAULT) 
//	public static EntityEntry entityCurseBush = EntityEntryBuilder
//			.create()
//			.entity(EntityCurseBush.class)
//			.name("CurseBush")
//			.id("curse_bush", nextId++)
//			.tracker(32, 1, true)
//			.build();

	private static int nextId = 0;

	private static EntityEntry createEntityEntry(Class<? extends Entity> clazz, String name, int range, int updateFrequency, boolean sendVelocityUpdate) {
		return EntityEntryBuilder
				.create()
				.entity(clazz)
				.name(name)
				.id(NameAndTabUtils.toId(name), nextId++)
				.tracker(range, updateFrequency, sendVelocityUpdate)
				.build();
	}
}