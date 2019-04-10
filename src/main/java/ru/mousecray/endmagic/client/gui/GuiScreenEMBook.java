package ru.mousecray.endmagic.client.gui;

import com.google.common.collect.ImmutableMap;
import com.sun.istack.internal.NotNull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.PageContainer;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class GuiScreenEMBook extends GuiScreen {

    public static GuiScreenEMBook instance = new GuiScreenEMBook();

    private GuiButton buttonBack;
    private NextPageButton buttonNextPage, buttonPreviousPage;
    private static int bookFullWidth = 256;
    private static int bookFullHeight = 192;

    public void setCurrentPage(Optional<PageContainer> currentPage) {
        this.currentPage = currentPage.orElse(BookApi.mainPage());
        updateButtons();
    }

    public void setCurrentPage(@NotNull PageContainer page) {
        currentPage = page;
        updateButtons();
    }

    private PageContainer currentPage = BookApi.mainPage();

    private ImmutableMap<String, PageContainer> bookContent = BookApi.getBookContent();

    private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(EM.ID, "textures/gui/book.png");

    @Override
    public void initGui() {
        buttonList.clear();
        int i = (width - bookFullWidth) / 2;
        int j = (height - bookFullHeight) / 2;

        buttonBack = addButton(new BackButton(0, width / 2 - 11, j + 160));

        buttonNextPage = addButton(new NextPageButton(1, i + 215, j + 160, true));
        buttonPreviousPage = addButton(new NextPageButton(2, i + 18, j + 160, false));

//        for(BookChapter chapter : bookContent.values()) {
//        	for(IPageComponent component : chapter.getChapterComponents()) {
//        		if(component.getComponentType() == ComponentType.LINK) {
//        			addButton((PageButton)component);
//        		}
//        	}       	
//        	genPage(chapter, 0);
//        }

        updateButtons();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int i = (width - bookFullWidth) / 2;
        int j = (height - bookFullHeight) / 2;
        if (mouseX < i + bookFullWidth / 2)
            performClickOnPage(mouseX, mouseY, i + 20, j + 15, currentPage.page1);
        else
            performClickOnPage(mouseX, mouseY, i + bookFullWidth / 2 + 4, j + 15, currentPage.page2);
    }

    private void performClickOnPage(int mouseX, int mouseY, int i, int j, IPage page) {
        page.elements().forEach(e -> {
            if (e instanceof IClickable && ((IClickable) e).area().contains(mouseX - i, mouseY - j, width, height))
                ((IClickable) e).click();

        });
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button instanceof PageButton)
            setCurrentPage(((PageButton) button).goToPage.get());
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    private void updateButtons() {
        buttonPreviousPage.visible = currentPage.left.isPresent();
        buttonNextPage.visible = currentPage.right.isPresent();
        buttonBack.visible = currentPage != BookApi.mainPage();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BOOK_TEXTURES);
        int i = (width - bookFullWidth) / 2;
        int j = (height - bookFullHeight) / 2;
        drawTexturedModalRect(i, j, 0, 0, bookFullWidth, bookFullHeight);

        drawPage(i + 20, j + 15, currentPage.page1, mouseX, mouseY);
        drawPage(i + bookFullWidth / 2 + 4, j + 15, currentPage.page2, mouseX, mouseY);


        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }

    private void drawPage(int i, int j, IPage page, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(i, j, 0);
        page.elements().forEach(e -> e.render(mouseX - i, mouseY - j));

        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    static class PageButton extends GuiButton {

        private final int u;
        private final int v;
        public final Supplier<Optional<PageContainer>> goToPage;

        public PageButton(int buttonId, int x, int y, int u, int v, Supplier<Optional<PageContainer>> goToPage) {
            super(buttonId, x, y, 23, 13, "");
            this.u = u;
            this.v = v;
            this.goToPage = goToPage;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (visible) {
                boolean flag = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(BOOK_TEXTURES);
                drawTexturedModalRect(x, y, flag ? u + 23 : u, v, 23, 13);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    static class NextPageButton extends PageButton {

        public NextPageButton(int button, int x, int y, boolean isForward) {
            super(button, x, y, 0, isForward ? bookFullHeight : bookFullHeight + 13,
                    isForward ? () -> GuiScreenEMBook.instance.currentPage.right : () -> GuiScreenEMBook.instance.currentPage.left);
        }
    }

    @SideOnly(Side.CLIENT)
    static class BackButton extends PageButton {
        public BackButton(int button, int x, int y) {
            super(button, x, y, 0, 218, () -> Optional.ofNullable(BookApi.mainPage()));
        }
    }
}