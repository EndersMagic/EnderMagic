package ru.mousecray.endmagic.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;

@SideOnly(Side.CLIENT)
public class GuiScreenTest extends GuiScreen
{
    private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(EM.ID, "textures/gui/book.png");
    private static int bookFullWidth = 256;
    private static int bookFullHeight = 192;
    GuiButtonExt buttonTp;

    @Override
    public void initGui()
    {
        buttonTp = new GuiButtonExt(0, width / 2 - 100, height / 2, "Teleprt to anower demension");
        buttonList.add(buttonTp);
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BOOK_TEXTURES);
        int i = (width - bookFullWidth) / 2;
        int j = (height - bookFullHeight) / 2;
        drawTexturedModalRect(i, j, 0, 0, bookFullWidth, bookFullHeight);

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }
    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id  == 0)
        {
            if (mc.player.dimension == 0) mc.player.changeDimension(1);
            else if (mc.player.dimension == 1) mc.player.changeDimension(0);
        }
    }
}
