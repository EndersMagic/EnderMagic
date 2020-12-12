package ru.mousecray.endmagic.client.render.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.player.EmCapability;
import ru.mousecray.endmagic.capability.player.EmCapabilityProvider;
import ru.mousecray.endmagic.items.inscribers.BaseInscriber;
import ru.mousecray.endmagic.rune.RuneColor;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

import java.util.Arrays;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HOTBAR;
import static org.lwjgl.opengl.GL11.*;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class EmOverlayIndicator extends Gui {

    private static int visible = 0;
    private static int prevVisible = 0;
    private static final int maxVisible = 20;//ticks
    private static RuneColor activeColor = RuneColor.Air;

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (mc().player != null) {

            Item itemOffhand = mc().player.getHeldItemOffhand().getItem();
            Item itemMainhand = mc().player.getHeldItemMainhand().getItem();

            if (itemOffhand instanceof BaseInscriber || itemMainhand instanceof BaseInscriber) {
                if (itemMainhand instanceof BaseInscriber)
                    activeColor = ((BaseInscriber) itemMainhand).runeColor();
                else
                    activeColor = ((BaseInscriber) itemOffhand).runeColor();

                if (visible < maxVisible)
                    visible++;
            } else if (visible > 0)
                visible--;

            prevVisible = visible;

        }

    }

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == HOTBAR) {

            GlStateManager.pushMatrix();
            {
                float colorAlpha = (prevVisible + (visible - prevVisible) * mc().getRenderPartialTicks()) / maxVisible;
                renderIndicatorLines(colorAlpha);
                //renderIndicatorCircleOne();
                //renderIndicatorCircleTwo();
                GlStateManager.color(1, 1, 1, 1);
            }
            GlStateManager.popMatrix();
        }
    }

    private static void renderIndicatorCircleOne() {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, 0);

        EmCapability capa = EmCapabilityProvider.getCapa(mc().player);

        //renderProgressPie(0,0,1);
        double segmentAngle = Math.PI * 2 / RuneColor.values().length;
        double angleOffset = (Math.PI / 2 - segmentAngle) / 2;

        GlStateManager.translate(20, 20, 0);

        for (int i = 0; i < RuneColor.values().length; i++) {
            RuneColor runeColor = RuneColor.values()[i];
            int em = capa.getEm(runeColor);
            int maxEm = capa.getMaxEm(runeColor);
            renderEmSegmentOne(runeColor, em, maxEm, segmentAngle * i - segmentAngle * Arrays.asList(RuneColor.values()).indexOf(activeColor) + angleOffset);
        }


        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);

    }

    private static void renderEmSegmentOne(RuneColor runeColor, int em, int maxEm, double additionRotation) {
        double segmentAngle = Math.PI * 2 / RuneColor.values().length;

        double radius = 0.02 * maxEm;

        int segmentDetalization = 100;

        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        for (int i = segmentDetalization; i > 0; i--) {
            double angle = (double) i / segmentDetalization * segmentAngle + additionRotation;
            buf.pos(radius * cos(angle), radius * sin(angle), 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        }
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        Tessellator.getInstance().draw();

        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        for (int i = (int) (segmentDetalization * ((double) em / maxEm)); i > 0; i--) {
            double angle = (double) i / segmentDetalization * segmentAngle + additionRotation;
            buf.pos(radius * cos(angle), radius * sin(angle), 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        }
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        Tessellator.getInstance().draw();

    }

    private static void renderIndicatorCircleTwo() {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, 0);

        EmCapability capa = EmCapabilityProvider.getCapa(mc().player);

        //renderProgressPie(0,0,1);
        double segmentAngle = Math.PI * 2 / RuneColor.values().length;
        double angleOffset = (Math.PI / 2 - segmentAngle) / 2;

        GlStateManager.translate(20, 20, 0);

        for (int i = 0; i < RuneColor.values().length; i++) {
            RuneColor runeColor = RuneColor.values()[i];
            int em = capa.getEm(runeColor);
            int maxEm = capa.getMaxEm(runeColor);
            renderEmSegmentTwo(runeColor, em, maxEm, segmentAngle * i - segmentAngle * Arrays.asList(RuneColor.values()).indexOf(activeColor) + angleOffset);
        }


        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
    }

    private static void renderEmSegmentTwo(RuneColor runeColor, int em, int maxEm, double additionRotation) {
        double segmentAngle = Math.PI * 2 / RuneColor.values().length;

        double radius = 0.02 * maxEm;

        int segmentDetalization = 100;

        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        for (int i = segmentDetalization; i > 0; i--) {
            double angle = (double) i / segmentDetalization * segmentAngle + additionRotation;
            buf.pos(radius * cos(angle), radius * sin(angle), 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        }
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        Tessellator.getInstance().draw();

        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        for (int i = segmentDetalization; i > 0; i--) {
            double angle = (double) i / segmentDetalization * segmentAngle + additionRotation;
            buf.pos(radius * ((double) em / maxEm) * cos(angle), radius * ((double) em / maxEm) * sin(angle), 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        }
        buf.pos(0, 0, 0).color(runeColor.r, runeColor.g, runeColor.b, 128).endVertex();
        Tessellator.getInstance().draw();

    }

    private static void renderIndicatorLines(float colorAlpha) {
        ScaledResolution scaledresolution = new ScaledResolution(mc());
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        FontRenderer fontrenderer = mc().fontRenderer;

        EmCapability capa = EmCapabilityProvider.getCapa(mc().player);
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
                drawRect(i1 * 10, 0, i1 * 10 + 10, capa.getMaxEm(color) / 10 + 2, new RGBA(color.r, color.g, color.b, (int) (70 * colorAlpha)).argb());
                drawRect(i1 * 10 + 1, 1, i1 * 10 + 9, capa.getEm(color) / 10 + 1, new RGBA(color.r, color.g, color.b, (int) (256 * colorAlpha)).argb());
            }
        }
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
    }

    private static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    private static int calculateRelativeValue(int i, double current, int max) {
        return (int) (i * (current / max));
    }
}
