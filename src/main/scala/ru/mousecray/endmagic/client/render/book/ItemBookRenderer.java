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
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.PageContainer;

import static org.lwjgl.opengl.GL11.*;
import static ru.mousecray.endmagic.client.render.book.ItemBookRenderer.BookState.*;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class ItemBookRenderer extends TileEntityItemStackRenderer {

    private static final ModelBook model = new ModelBook();
    public Minecraft mc = Minecraft.getMinecraft();

    public enum BookState {
        opening(true), opened(false), list_right(true), list_left(true), closing(true), closed(false);

        public final boolean isAnimated;

        BookState(boolean isAnimated) {
            this.isAnimated = isAnimated;
        }
    }

    public static PageContainer currentPage;

    public static BookState state = closed;
    public static float animationOpening = 0;
    public static float animationListing = 0;

    public static void setState(BookState next) {
        if (state == opening && next == closing || state == closing && next == opening ||
                state == opened && (next == list_left || next == list_right || next == closing) ||
                state == closed && next == opening
        )
            state = next;

        if (next == list_left)
            animationListing = 1;
        else if (next == list_right)
            animationListing = 0;
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
        glTranslatef(0.0f, 0.0f, -2000.0f);
    }

    private static void popOverlayRendering() {
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
    }

    private void renderCover(float partialTick) {

        if(false){//рендер просто стекстурой
            mc.getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"));
            GlStateManager.translate(0, 1, 1);
            render(0F, 0F, animationListing, animationOpening, 0F, 1F / 16F);

        }else{//рендер через фреймбуфер-посредник
            int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D); // в 1.12 нужно сохранять текстур атлас

            framebuffer().bindFramebuffer(true);//типо, рендер текстуры во фреймбуффер
            {
                //pushOverlayRendering();

                //GL11.glBindTexture(GL_TEXTURE_2D, 0);
                //GL11.glColor4f(0f, 1f, 0f, 1f);
                mc.getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"));
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(0, 0, 0).tex(0, 0).endVertex();
                buffer.pos(0, framebuffer().framebufferHeight, 0).tex(0, 1).endVertex();
                buffer.pos(framebuffer().framebufferWidth, framebuffer().framebufferHeight, 0).tex(1, 1).endVertex();
                buffer.pos(framebuffer().framebufferWidth, 0, 0).tex(1, 0).endVertex();
                tessellator.draw();

                //popOverlayRendering();

            }
            framebuffer().unbindFramebuffer();

            mc.getFramebuffer().bindFramebuffer(true);
            GlStateManager.pushMatrix();
            {
                framebuffer().bindFramebufferTexture();//рендер модели и спользованием фреймбуфера
                //GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffer().framebufferTexture);
                GlStateManager.translate(0, 1, 1);
                render(0F, 0F, animationListing, animationOpening, 0F, 1F / 16F);
            }
            GlStateManager.popMatrix();

            framebuffer().framebufferClear();
            mc.getFramebuffer().bindFramebuffer(true);
            GL11.glBindTexture(GL_TEXTURE_2D, current);
        }

        // if (currentPage != null) {

        /*
        //drawPage(0, 15, currentPage.page1, 0, 0);
        //drawPage(bookFullWidth / 2 + 4, 15, currentPage.page2, 0, 0);
        */
    }

    public void render(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, null);
        model.coverRight.render(scale);
        model.coverLeft.render(scale);
        model.bookSpine.render(scale);
        GlStateManager.translate(0.01, 0, 0);
        model.pagesRight.render(scale);
        model.pagesLeft.render(scale);
        model.flippingPageRight.render(scale);
        model.flippingPageLeft.render(scale);
    }
}
