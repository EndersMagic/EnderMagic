package ru.mousecray.endmagic.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;

import static ru.mousecray.endmagic.network.PacketTypes.CHANDE_DEMENSION;
import static ru.mousecray.endmagic.network.PacketTypes.STRUCTURE_FIND;

@SideOnly(Side.CLIENT)
public class GuiScreenTest extends GuiScreen {

    private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(EM.ID, "textures/gui/book.png");
    private static int bookFullWidth = 256;
    private static int bookFullHeight = 192;

    @Override
    public void initGui() {
        super.initGui();
        WorldServer[] worlds = DimensionManager.getWorlds();
        for (int i = 0; i < worlds.length; i++) {
            WorldServer world = worlds[i];
            buttonList.add(new GuiButtonExt(world.provider.getDimension(), width / 2 - 100, 50 + i * 30, "Teleprt to " + world.provider.getDimensionType().getName() + " demension"));
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft mc = Minecraft.getMinecraft();
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
    protected void actionPerformed(GuiButton button) {
        if (button.displayString.startsWith("Teleprt to ") && button.displayString.endsWith(" demension"))
            CHANDE_DEMENSION.packet().writeInt(button.id).sendToServer();
        else if (button.displayString.startsWith("Teleprt to ") && button.displayString.endsWith(" structure"))
            STRUCTURE_FIND.packet().writeString(button.displayString.substring("Teleprt to ".length(), button.displayString.length() - " structure".length())).sendToServer();
    }
}
