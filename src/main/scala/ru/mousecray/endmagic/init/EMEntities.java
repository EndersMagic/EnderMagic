package ru.mousecray.endmagic.init;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import ru.mousecray.endmagic.entity.EntityCurseBush;
import ru.mousecray.endmagic.entity.EntityEMEnderPearl;
import ru.mousecray.endmagic.entity.EntityEnderArrow;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

@MethodsReturnNonnullByDefault
public class EMEntities {
    private static int nextId = 0;

    public static final EntityEntry emEnderPearl = createEntityEntry(EntityEMEnderPearl.class, "EmEnderPearl", 128, 1, true);
    public static final EntityEntry curseBush = createEntityEntry(EntityCurseBush.class, 128, 1, true);
    public static final EntityEntry EnderArrow = createEntityEntry(EntityEnderArrow.class, 128, 1, true);

    private static EntityEntry createEntityEntry(Class<? extends Entity> clazz, int range, int updateFrequency, boolean sendVelocityUpdate) {
        return createEntityEntry(clazz, NameAndTabUtils.getName(clazz, string -> string.substring(6)), range, updateFrequency, sendVelocityUpdate);
    }

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