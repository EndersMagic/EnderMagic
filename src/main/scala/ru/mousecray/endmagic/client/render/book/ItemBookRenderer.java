package ru.mousecray.endmagic.client.render.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.mousecray.endmagic.EM;

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

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        float partialTick = mc.getRenderPartialTicks();
        renderCover(partialTick);
    }

    private void renderCover(float partialTick) {
        mc.getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"));

        GlStateManager.translate(0, 1, 1);

        render(0F, 0F, animationListing, animationOpening, 0F, 1F / 16F);
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
