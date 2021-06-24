package ru.mousecray.endmagic.client.render.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

@Mod.EventBusSubscriber(modid = EM.ID)
public class TestOverlay {

    @SubscribeEvent
    public static void onRenderTest(RenderGameOverlayEvent.Pre event) {
        PageTextureHolder.freeTexture(BookApi.mainChapter());
        Framebuffer texture = PageTextureHolder.getTexture(BookApi.mainChapter());
        drawPageContainerRect(texture);

        //PageRenderer.drawPageContainerBackRect();
        //PageRenderer.drawPageContainerContent();
        //drawPageContainerRectOriginal();
    }

    static int x = 10;
    static int y = 10;
    static int w = 260;
    static int h = 208;

    private static void drawPageContainerRect(Framebuffer framebuffer) {
        int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

        framebuffer.bindFramebufferTexture();
        {

            BufferBuilder buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(x, y, 0).tex(0, 1).color(255, 255, 255, 255).endVertex();
            buffer.pos(x, y + h, 0).tex(0, 0).color(255, 255, 255, 255).endVertex();
            buffer.pos(x + w, y + h, 0).tex(1, 0).color(255, 255, 255, 255).endVertex();
            buffer.pos(x + w, y, 0).tex(1, 1).color(255, 255, 255, 255).endVertex();
            Tessellator.getInstance().draw();
        }
        framebuffer.unbindFramebufferTexture();

        {
            //framebuffer.framebufferClear();
            //mc.getFramebuffer().bindFramebuffer(true);
            GL11.glBindTexture(GL_TEXTURE_2D, current);
        }
    }

    private static void drawPageContainerRectOriginal() {
        int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

        //framebuffer.bindFramebufferTexture();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page.png"));
        {

            BufferBuilder buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(x + w + 10, y, 0).tex(0, 0).color(255, 255, 255, 255).endVertex();
            buffer.pos(x + w + 10, y + h, 0).tex(0, 1).color(255, 255, 255, 255).endVertex();
            buffer.pos(x + w + w + 10, y + h, 0).tex(1, 1).color(255, 255, 255, 255).endVertex();
            buffer.pos(x + w + w + 10, y, 0).tex(1, 0).color(255, 255, 255, 255).endVertex();
            Tessellator.getInstance().draw();
        }
        //framebuffer.unbindFramebufferTexture();

        {
            //framebuffer.framebufferClear();
            //mc.getFramebuffer().bindFramebuffer(true);
            GL11.glBindTexture(GL_TEXTURE_2D, current);
        }
    }
}
