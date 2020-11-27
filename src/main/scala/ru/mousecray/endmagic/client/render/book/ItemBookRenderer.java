package ru.mousecray.endmagic.client.render.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.client.gui.elements.LinkElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.GL_QUADS;
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
    public static PageContainer nextPage;

    public static BookState state = closed;
    public static double animationOpening = 0;
    public static double animationListing = 0;

    private static double prevAnimationOpening = 0;
    private static double prevAnimationListing = 0;

    public static void setAnimationOpening(double v) {
        prevAnimationOpening = animationOpening;
        animationOpening = v;
    }

    public static void setAnimationListing(double v) {
        prevAnimationListing = animationListing;
        animationListing = v;
    }

    public static void setState(BookState next) {
        if (state == opening && next == closing || state == closing && next == opening ||
                state == opened && (next == list_left && currentPage.left.isPresent() || next == list_right && currentPage.right.isPresent() || next == closing) ||
                state == closed && next == opening
        ) {
            state = next;

            if (next == list_left) {
                animationListing = 1;
                prevAnimationListing = 1;
                nextPage = currentPage.left.get();
            } else if (next == list_right) {
                animationListing = 0;
                prevAnimationListing = 0;
                nextPage = currentPage.right.get();
            }
        }
    }

    public static void listTo(BookState next, PageContainer nextPage) {
        if (state == opened && (next == list_left || next == list_right)) {
            state = next;
            ItemBookRenderer.nextPage = nextPage;

            if (next == list_left) {
                animationListing = 1;
                prevAnimationListing = 1;
            } else if (next == list_right) {
                animationListing = 0;
                prevAnimationListing = 0;
            }
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
        else if (Keyboard.isKeyDown(Keyboard.KEY_K))
            listTo(Minecraft.getMinecraft().world.rand.nextBoolean() ? list_left : list_right, BookApi.mainChapter());
        else if (currentPage != null) {
            List<LinkElement> links = currentPage.page1.elements().stream().filter(e -> e instanceof LinkElement).map(e -> ((LinkElement) e)).collect(Collectors.toList());
            try {
                if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1))
                    nextPage = links.get(0).page;
                else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2))
                    nextPage = links.get(1).page;
                else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3))
                    nextPage = links.get(2).page;
                else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4))
                    nextPage = links.get(3).page;
                else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5))
                    nextPage = links.get(4).page;
                else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6))
                    nextPage = links.get(5).page;
                else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7))
                    nextPage = links.get(6).page;
                else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8))
                    nextPage = links.get(7).page;

                if (nextPage != null)
                    listTo(Minecraft.getMinecraft().world.rand.nextBoolean() ? list_left : list_right, nextPage);
            } catch (IndexOutOfBoundsException ignored) {

            }
        }

    }

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {

        ItemSword
        Minecraft.getMinecraft().player.getHeldItemOffhand()
        if (state == opening) {
            if (animationOpening >= 1) {
                state = opened;
                prevAnimationOpening = animationOpening;
            } else
                setAnimationOpening(animationOpening + 0.02);

        } else if (state == closing) {
            if (animationOpening <= 0) {
                state = closed;
                prevAnimationOpening = animationOpening;
                animationListing = 0;
                prevAnimationListing = 0;
            } else
                setAnimationOpening(animationOpening - 0.02);

        } else if (state == list_left) {
            if (animationListing <= 0) {
                state = opened;
                prevAnimationListing = animationListing;
                currentPage = nextPage;
                nextPage = null;
            } else
                setAnimationListing(animationListing - 0.02);

        } else if (state == list_right) {
            if (animationListing >= 1) {
                state = opened;
                prevAnimationListing = animationListing;
                currentPage = nextPage;
                nextPage = null;
            } else
                setAnimationListing(animationListing + 0.02);

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_P))
            rotation += 1;
    }

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        float partialTick = mc.getRenderPartialTicks();
        renderCover(partialTick);
    }

    private void renderCover(float partialTick) {
        GlStateManager.color(1, 1, 1);
        GlStateManager.pushMatrix();
        {
            mc.getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"));
            GlStateManager.translate(0, 1, 1);
            GlStateManager.rotate(rotation, 0, 0, -1);
            render(0F, 0F, prevAnimationListing + (animationListing - prevAnimationListing) * partialTick, prevAnimationOpening + (animationOpening - prevAnimationOpening) * partialTick, 0F, scale);


            //GlStateManager.disableDepth();

            testFont();

            GlStateManager.enableDepth();
        }
        GlStateManager.popMatrix();

    }

    private void testFont() {


        if (currentPage != null) {
            if (true) {
                GlStateManager.pushMatrix();
                {
                    transformForBookPart(model.pagesLeft);
                    transformForFirstPage();
                    //testQuad();
                    drawPage(0, 0, state == list_left ? nextPage.page1 : currentPage.page1, 0, 0);
                }
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                {
                    transformForBookPart(model.pagesRight);
                    transformForSecondPage();
                    //testQuad();
                    drawPage(0, 0, state == list_right ? nextPage.page2 : currentPage.page2, 0, 0);
                }
                GlStateManager.popMatrix();
            }

            if (state == list_left || state == list_right) {
                GlStateManager.pushMatrix();
                {
                    transformForBookPart(model.flippingPageLeft);
                    transformForFirstPage();
                    //testQuad();
                    drawPage(0, 0, state == list_right ? nextPage.page1 : currentPage.page1, 0, 0);
                }
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                {
                    transformForBookPart(model.flippingPageLeft);
                    transformForSecondPage();
                    //testQuad();
                    drawPage(0, 0, state == list_left ? nextPage.page2 : currentPage.page2, 0, 0);
                }
                GlStateManager.popMatrix();
            }
        }

    }

    private void transformForFirstPage() {
        GlStateManager.rotate(180, 0, 0, 1);
        GlStateManager.scale(0.00155, 0.00155, 0.00155);
        GlStateManager.translate(-180, -140, -0.7);
    }

    private void transformForSecondPage() {
        GlStateManager.rotate(180, 1, 0, 0);
        GlStateManager.scale(0.00155, 0.00155, 0.00155);
        GlStateManager.translate(20, -140, -0.7);
    }

    private void transformForBookPart(ModelRenderer pagesRight) {
        GlStateManager.translate(pagesRight.offsetX, pagesRight.offsetY, pagesRight.offsetZ);
        GlStateManager.translate(pagesRight.rotationPointX * scale, pagesRight.rotationPointY * scale, pagesRight.rotationPointZ * scale);
        GlStateManager.rotate(pagesRight.rotateAngleY * (180F / (float) Math.PI), 0, 1, 0);
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

    public void render(float limbSwing, float limbSwingAmount, double ageInTicks, double netHeadYaw, float headPitch, float scale) {
        model.setRotationAngles(limbSwing, limbSwingAmount, (float) ageInTicks, (float) netHeadYaw, headPitch, scale, null);
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
