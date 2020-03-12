package ru.mousecray.endmagic.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import ru.mousecray.endmagic.entity.EntityEMEnderPearl;
import ru.mousecray.endmagic.init.util.IRegistryMapSource;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

public class EMEntities {
	
	public static EntityEntry emEnderPearl = createEntityEntry(EntityEMEnderPearl.class, "EMEnderPearl", 64, 20, true);
	
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

	private static final class ClassFieldEntitySource
			implements IRegistryMapSource<EnumEntityRegistryType, EntityEntry> {
		public final Class sourceClass;
		private final boolean traceErrors;

		public ClassFieldEntitySource(Class sourceClass, boolean traceErrors) {
			this.sourceClass = sourceClass;
			this.traceErrors = traceErrors;
		}

		@Override
		public Map<EnumEntityRegistryType, EntityEntry> elemes() {
			return Arrays.stream(sourceClass.getFields())
					.filter(field -> Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()))
					.flatMap(field -> {
						try {
							EntityEntry elem = (EntityEntry) field.get(null);
							EMEntity an = field.getAnnotationsByType(EMEntity.class)[0];
							return Stream.of(new SimpleEntry<EnumEntityRegistryType, EntityEntry>(an.type(), elem));
						} catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
							if (traceErrors) {
								System.out.println("Problem with field: " + field.getName());
								e.printStackTrace();
							}
							return Stream.empty();
						}
					}).collect(Collectors.<SimpleEntry<EnumEntityRegistryType, EntityEntry>, EnumEntityRegistryType, EntityEntry>toMap(entry -> {
						return entry.getKey();
					}, entry -> {				
						return entry.getValue();
					}));
		}

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private static @interface EMEntity { EnumEntityRegistryType type(); }

	public static enum EnumEntityRegistryType {
		DEFAULT, WITH_SPAWN;
	}
}