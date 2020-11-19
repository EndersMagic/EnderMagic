package ru.mousecray.endmagic.client.render.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.player.EmCapabilityProvider;
import ru.mousecray.endmagic.capability.player.IEmCapability;

import java.util.EnumSet;
import java.util.Set;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.*;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class EmOverlayIndicator extends Gui {

    private static Set<RenderGameOverlayEvent.ElementType> shiftedElements = EnumSet.of(HEALTH, ARMOR, FOOD);

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (shiftedElements.contains(event.getType()))
            GlStateManager.translate(0, -10, 0);
    }

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (shiftedElements.contains(event.getType()))
            GlStateManager.translate(0, 10, 0);

        if (event.getType() == HEALTH) {
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution scaledresolution = new ScaledResolution(mc);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            FontRenderer fontrenderer = mc.fontRenderer;

            IEmCapability capa = EmCapabilityProvider.getCapa(mc.player);

            int indicatorWidht = 180;
            int indicatorHeight = 7;
            int startX = i / 2 - indicatorWidht / 2;
            int startY = j - 38;
            drawRect(startX, startY, startX + indicatorWidht, startY + indicatorHeight, 0xffffffff);
            //drawRect(startX, startY, startX + indicatorWidht * capa.getEm() / capa.getMaxEm(), startY + indicatorHeight, 0xffff00ff);
            GlStateManager.color(1, 1, 1, 1);
        }
    }
}
