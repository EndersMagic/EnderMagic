package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.fml.common.Mod;
import ru.mousecray.endmagic.EM;

@Mod.EventBusSubscriber(modid = EM.ID)
public class ByDragonBlockBreaking {
    public static void onDragonBreak(LivingDestroyBlockEvent event) {
        if (event.getEntity() instanceof EntityDragon)
            if (event.getState().getBlock() instanceof IPolishedObsidian)
                event.setCanceled(true);
    }
}
