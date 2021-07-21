package ru.mousecray.endmagic.util.render;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.mousecray.endmagic.EM;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class ClientTickHandler {
    private static int tick = 0;

    public static float fullTicks() {
        return (float) tick + Minecraft.getMinecraft().getRenderPartialTicks();
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        tick++;
    }
}
