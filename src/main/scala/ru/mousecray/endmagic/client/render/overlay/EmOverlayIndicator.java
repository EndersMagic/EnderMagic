package ru.mousecray.endmagic.client.render.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.player.EmCapability;
import ru.mousecray.endmagic.capability.player.EmCapabilityProvider;
import ru.mousecray.endmagic.rune.RuneColor;

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
            int startY = j - 37;

            GlStateManager.depthMask(false);
            GlStateManager.glLineWidth(10);
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(GL_GREATER, 0);
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();

                drawLineAt(capa, fullMaxEm, indicatorWidth, startX, startY, tessellator, buffer);
                drawLineAt(capa, fullMaxEm, indicatorWidth, startX, startY + 2.5, tessellator, buffer);
                drawLineAt(capa, fullMaxEm, indicatorWidth, startX, startY + 5, tessellator, buffer);
            }
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);

            //drawRect(startX, startY, startX + indicatorWidth, startY + indicatorHeight, 0xffffffff);
            //drawRect(startX, startY, startX + indicatorWidht * capa.getEm() / capa.getMaxEm(), startY + indicatorHeight, 0xffff00ff);
            GlStateManager.color(1, 1, 1, 1);
        }
    }

    private static void drawLineAt(EmCapability capa, int fullMaxEm, int indicatorWidth, int startX, double startY, Tessellator tessellator, BufferBuilder buffer) {
        buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);

        int currentPos = 0;

        for (RuneColor color : RuneColor.values()) {
            int alpha = calculateRelativeValue(255, capa.getEm(color), capa.getMaxEm(color));
            buffer.pos(startX + currentPos, startY, 0).color(color.r, color.g, color.b, alpha).endVertex();
            currentPos += calculateRelativeValue(indicatorWidth, capa.getMaxEm(color), fullMaxEm);
        }

        tessellator.draw();
    }

    private static int calculateRelativeValue(int i, double current, int max) {
        return (int) (i * (current / max));
    }
}
