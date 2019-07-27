package ru.mousecray.endmagic.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;

public class GuiBlastFurnace extends GuiContainer {
    private ContainerBlastFurnace container;

    public GuiBlastFurnace(ContainerBlastFurnace containerBlastFurnace) {
        super(containerBlastFurnace);
        container = containerBlastFurnace;
        xSize = 190;
        ySize = 234;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }


    static ResourceLocation background = new ResourceLocation(EM.ID, "textures/gui/blast_furnace.png");

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
