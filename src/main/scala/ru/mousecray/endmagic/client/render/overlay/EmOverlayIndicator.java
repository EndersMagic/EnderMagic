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
import ru.mousecray.endmagic.capability.player.EmCapability;
import ru.mousecray.endmagic.capability.player.EmCapabilityProvider;
import ru.mousecray.endmagic.rune.RuneColor;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.*;
import static org.lwjgl.opengl.GL11.*;

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

            EmCapability capa = EmCapabilityProvider.getCapa(mc.player);
            int fullMaxEm = Arrays.stream(RuneColor.values()).mapToInt(capa::getMaxEm).sum();


            int indicatorWidth = 180;
            int indicatorHeight = 7;
            int startX = i / 2 - indicatorWidth / 2;
            int startY = 10;

            GlStateManager.depthMask(false);
            GlStateManager.glLineWidth(10);
            GlStateManager.disableTexture2D();
            //GlStateManager.shadeModel(GL11.GL_SMOOTH);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(GL_GREATER, 0);
            {


                RuneColor[] values = RuneColor.values();
                for (int i1 = 0; i1 < values.length; i1++) {
                    RuneColor color = values[i1];
                    drawRect(i1*10, 0, i1*10 + 10, capa.getMaxEm(color) / 10+2, new RGBA(color.r, color.g, color.b, 70).argb());
                    drawRect(i1*10+1, 1, i1*10 + 9, capa.getEm(color) / 10+1, new RGBA(color.r, color.g, color.b, 256).argb());
                }
            }
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);

            //drawRect(startX, startY, startX + indicatorWidth, startY + indicatorHeight, 0xffffffff);
            //drawRect(startX, startY, startX + indicatorWidht * capa.getEm() / capa.getMaxEm(), startY + indicatorHeight, 0xffff00ff);
            GlStateManager.color(1, 1, 1, 1);
        }
    }

    private static int calculateRelativeValue(int i, double current, int max) {
        return (int) (i * (current / max));
    }
}
