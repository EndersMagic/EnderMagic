package ru.mousecray.endmagic.client.render.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.api.embook.components.ImageComponent;
import ru.mousecray.endmagic.api.embook.components.RecipeComponent;
import ru.mousecray.endmagic.api.embook.components.TextComponent;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

@Mod.EventBusSubscriber(modid = EM.ID)
public class TestOverlay {

    @SubscribeEvent
    public static void onRenderTest(RenderGameOverlayEvent.Pre event) {
        //Test.test();

        PageTextureHolder.freeAll();
/*
        PageContainer page = testPage();//cycleElementOf(BookApi.allPages, BookApi.mainChapter());

        Framebuffer fbo = PageTextureHolder.getTexture(page);

        GlStateManager.enableTexture2D();
        drawPageContainerRect(fbo);

        //System.out.println(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);*/

    }

    private static PageContainer testPage;

    public static PageContainer testPage() {
        if (testPage == null || Keyboard.isKeyDown(Keyboard.KEY_H))
            testPage = new PageContainer(
                    new RecipeComponent(new ItemStack(EMBlocks.blockMasterStaticPortal)).pages().get(0),
                    new TextComponent("book.chapter.text.teleport_construction").pages().get(0));
        //new TextComponent("book.chapter.text.teleport_construction").pages().get(0),
        //new TextComponent("book.chapter.text.teleport_construction").pages().get(0));
        //new ImageComponent(new ResourceLocation(EM.ID, "textures/book/compression_system_2.png"), "").pages().get(0)
        return testPage;
    }

    static <A> A cycleElementOf(List<A> list, A defaultValue) {
        if ((list.size() > 0))
            return list.get((int) (System.currentTimeMillis() / 1000L % list.size()));
        else
            return defaultValue;
    }

    static int x = 10;
    static int y = 10;
    static int w = Refs.pageContainerWidth()/Refs.resolutionMultiplier();
    static int h = Refs.pageHeight()/Refs.resolutionMultiplier();

    private static void drawPageContainerRect(Framebuffer framebuffer) {
        int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

        //Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page2.png"));
        framebuffer.bindFramebufferTexture();
        {

            //FBODumper.dump();

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
