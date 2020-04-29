package ru.mousecray.endmagic.client.gui;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;

import static ru.mousecray.endmagic.network.PacketTypes.CHANDE_DEMENSION;
import static ru.mousecray.endmagic.network.PacketTypes.STRUCTURE_FIND;

@SideOnly(Side.CLIENT)
public class GuiScreenTest extends GuiScreen
{
    private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(EM.ID, "textures/gui/book.png");
    private static int bookFullWidth = 256;
    private static int bookFullHeight = 192;
    GuiButtonExt buttonChD;
    //GuiButtonExt buttonFindStr;//to structure find todo

    @Override
    public void initGui()
    {
        buttonChD = new GuiButtonExt(0, width / 2 - 100, height / 2, "Teleprt to anower demension");
       // buttonFindStr = new GuiButtonExt(1, width / 2 - 100, height / 2 + 10 + 20, "Teleport to nearest str");//to structure find todo
        buttonList.add(buttonChD);
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
        switch (button.id)
        { case 0:
           CHANDE_DEMENSION.packet().sendToServer();
           break;
          default://to structure find todo
           STRUCTURE_FIND.packet().writeString("str name").sendToServer();
           break;
        }

    }
}
