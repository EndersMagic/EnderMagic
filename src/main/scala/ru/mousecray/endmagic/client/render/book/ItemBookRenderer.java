package ru.mousecray.endmagic.client.render.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.client.gui.GuiScreenEMBook;

import static org.lwjgl.opengl.GL11.*;
import static ru.mousecray.endmagic.client.gui.GuiScreenEMBook.drawPage;
import static ru.mousecray.endmagic.client.render.book.ItemBookRenderer.BookState.*;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class ItemBookRenderer extends TileEntityItemStackRenderer {

    private static final ModelBook model = new ModelBook();
    public Minecraft mc = Minecraft.getMinecraft();
    public float scale = 1F / 16F;

    public enum BookState {
        opening(true), opened(false), list_right(true), list_left(true), closing(true), closed(false);

        public final boolean isAnimated;

        BookState(boolean isAnimated) {
            this.isAnimated = isAnimated;
        }
    }

    public static float rotation = 0;

    public static PageContainer currentPage;

    public static BookState state = closed;
    public static float animationOpening = 0;
    public static float animationListing = 0;

    public static void setState(BookState next) {
        if (state == opening && next == closing || state == closing && next == opening ||
                state == opened && (next == list_left || next == list_right || next == closing) ||
                state == closed && next == opening
        ) {
            state = next;

            if (next == list_left)
                animationListing = 1;
            else if (next == list_right)
                animationListing = 0;
        }
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_Y))
            setState(opening);
        else if (Keyboard.isKeyDown(Keyboard.KEY_U))
            setState(closing);
        else if (Keyboard.isKeyDown(Keyboard.KEY_I))
            setState(list_left);
        else if (Keyboard.isKeyDown(Keyboard.KEY_O))
            setState(list_right);

    }

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (state == opening) {
            if (animationOpening >= 1)
                state = opened;
            else
                animationOpening += 0.01;

        } else if (state == closing) {
            if (animationOpening <= 0) {
                state = closed;
                animationListing = 0;
            } else
                animationOpening -= 0.01;

        } else if (state == list_left) {
            if (animationListing <= 0)
                state = opened;
            else
                animationListing -= 0.01;

        } else if (state == list_right) {
            if (animationListing >= 1)
                state = opened;
            else
                animationListing += 0.01;

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_P))
            rotation += 1;
    }

    @SubscribeEvent
    public static void textOverlay(RenderGameOverlayEvent event) {
        /*
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {

            int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D); // в 1.12 нужно сохранять текстур атлас

            framebuffer().bindFramebuffer(true);
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"));
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(0, 0, 0).tex(0, 0).endVertex();
                buffer.pos(0, framebuffer().framebufferHeight, 0).tex(0, 1).endVertex();
                buffer.pos(framebuffer().framebufferWidth, framebuffer().framebufferHeight, 0).tex(1, 1).endVertex();
                buffer.pos(framebuffer().framebufferWidth, 0, 0).tex(1, 0).endVertex();
                tessellator.draw();
            }
            framebuffer().unbindFramebuffer();

            Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);

            framebuffer().bindFramebufferTexture();


            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

            buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(0, 0, 0).tex(0, 0).endVertex();
            buffer.pos(0, framebuffer().framebufferHeight, 0).tex(0, 1).endVertex();
            buffer.pos(framebuffer().framebufferWidth, framebuffer().framebufferHeight, 0).tex(1, 1).endVertex();
            buffer.pos(framebuffer().framebufferWidth, 0, 0).tex(1, 0).endVertex();
            tessellator.draw();

            //renderBorder(tessellator, buffer);


            framebuffer().framebufferClear();
            Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
            GL11.glBindTexture(GL_TEXTURE_2D, current);
        }*/
    }

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        float partialTick = mc.getRenderPartialTicks();
        renderCover(partialTick);
    }

    private static class FBOHolder {
        static Framebuffer framebuffer;
    }

    private static Framebuffer framebuffer() {//ленивая инициализация, типо
        if (FBOHolder.framebuffer == null)
            FBOHolder.framebuffer = new Framebuffer(128, 64, false);
        return FBOHolder.framebuffer;
    }

    private static void pushOverlayRendering() {
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0.0, Display.getWidth(), Display.getHeight(), 0.0, 1000.0, 3000.0);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
    }

    private static void popOverlayRendering() {
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
    }

    private void renderCover(float partialTick) {
        GlStateManager.color(1, 1, 1);
        GlStateManager.pushMatrix();
        {
            mc.getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"));
            GlStateManager.translate(0, 1, 1);
            GlStateManager.rotate(rotation, 0, 0, -1);
            render(0F, 0F, animationListing, animationOpening, 0F, scale);


            //GlStateManager.disableDepth();

            testFont();
            //testQuad();
            //GlStateManager.translate(-0.30F, -0.24F, -0.07F);
            //GlStateManager.scale(0.0030F, 0.0030F, -0.0030F);
            //mc.getTextureManager().bindTexture(new ResourceLocation("textures/font/ascii.png"));
            //mc.fontRenderer.drawString("Test", 0, 0, 0xff00ff);

            GlStateManager.enableDepth();
        }
        GlStateManager.popMatrix();

    }

    private void testFont() {


        if (GuiScreenEMBook.instance.currentPage != null) {
            GlStateManager.pushMatrix();
            {
                transformForSecondPage();
                //testQuad();
                drawPage(0, 0, GuiScreenEMBook.instance.currentPage.page2, 0, 0);

            }
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            {
                transformForFirstPage();
                //testQuad();
                drawPage(0, 0, GuiScreenEMBook.instance.currentPage.page1, 0, 0);

            }
            GlStateManager.popMatrix();
        }

    }

    private void transformForFirstPage() {
        GlStateManager.translate(model.pagesLeft.offsetX, model.pagesLeft.offsetY, model.pagesLeft.offsetZ);
        GlStateManager.translate(model.pagesLeft.rotationPointX * scale, model.pagesLeft.rotationPointY * scale, model.pagesLeft.rotationPointZ * scale);
        GlStateManager.rotate(model.pagesLeft.rotateAngleY * (180F / (float) Math.PI), 0, 1, 0);
        GlStateManager.rotate(180, 0, 0, 1);
        GlStateManager.scale(0.00155, 0.00155, 0.00155);
        GlStateManager.translate(-180, -140, -0.7);
    }

    private void transformForSecondPage() {
        GlStateManager.translate(model.pagesRight.offsetX, model.pagesRight.offsetY, model.pagesRight.offsetZ);
        GlStateManager.translate(model.pagesRight.rotationPointX * scale, model.pagesRight.rotationPointY * scale, model.pagesRight.rotationPointZ * scale);
        GlStateManager.rotate(model.pagesRight.rotateAngleY * (180F / (float) Math.PI), 0, 1, 0);
        GlStateManager.rotate(180, 1, 0, 0);
        GlStateManager.scale(0.00155, 0.00155, 0.00155);
        GlStateManager.translate(20, -140, -0.7);
    }

    private static void testQuad() {
        int w = 160;
        int h = 280;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, 0, 0).tex(0, 1).endVertex();
        buffer.pos(0, h, 0).tex(0, 0.99).endVertex();
        buffer.pos(w, h, 0).tex(0.01, 0.99).endVertex();
        buffer.pos(w, 0, 0).tex(0.01, 1).endVertex();
        tessellator.draw();
    }

    public void render(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, null);
        model.coverRight.render(scale);
        model.coverLeft.render(scale);
        model.bookSpine.render(scale);
        GlStateManager.translate(0.01, 0, 0);
        model.pagesRight.render(scale);
        model.pagesLeft.render(scale);
        //model.flippingPageRight.render(scale);
        model.flippingPageLeft.render(scale);
    }
}
