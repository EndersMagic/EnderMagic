package ru.mousecray.endmagic.client.render.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;

public class ItemBookRenderer extends TileEntityItemStackRenderer {

    private static final ModelBook model = new ModelBook();
    public Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        float partialTick = mc.getRenderPartialTicks();
        renderCover(partialTick);
    }

    private void renderCover(float partialTick) {
        mc.getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"));

        GlStateManager.translate(0, 1, 1);
        render(0F, 0F, 0.5f, 1, 0F, 1F / 16F);
    }

    public void render(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, null);
        model.coverRight.render(scale);
        model.coverLeft.render(scale);
        model.bookSpine.render(scale);
        model.pagesRight.render(scale);
        model.pagesLeft.render(scale);
        model.flippingPageRight.render(scale);
        model.flippingPageLeft.render(scale);
    }
}
