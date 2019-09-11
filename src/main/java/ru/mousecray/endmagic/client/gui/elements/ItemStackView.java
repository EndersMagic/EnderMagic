package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.Rectangle;
import ru.mousecray.endmagic.client.gui.GuiScreenEMBook;
import ru.mousecray.endmagic.client.gui.IClickable;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.util.ItemStackMapKey;

import java.util.List;

public class ItemStackView implements IStructuralGuiElement, IClickable {
    public final ImmutableList<ItemStack> itemStack;
    public final int x;
    public final int y;
    private final Rectangle itemArea;

    public ItemStackView(ItemStack itemStack, int x, int y) {
        this(ImmutableList.of(itemStack), x, y);
    }

    public ItemStackView(ImmutableList<ItemStack> itemStack, int x, int y) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
        itemArea = new Rectangle(this.x, this.y, this.x + 16, this.y + 16);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        ItemStack stack = cycleItemStack(itemStack);
        if (!stack.isEmpty()) {
            drawItemStack(stack, x, y);
            if (itemArea.contains(mouseX, mouseY))
                renderTooltip(mouseX, mouseY, stack.getTooltip(mc().player, ITooltipFlag.TooltipFlags.NORMAL), 0x505000ff, 0xf0100010);
        }
    }

    public void drawItemStack(ItemStack stack, int x, int y) {
        GlStateManager.disableLighting();
        RenderItem itemRender = mc().getRenderItem();
        FontRenderer fontRenderer = mc().fontRenderer;
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        itemRender.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.zLevel = 0.0F;
    }

    private ItemStack cycleItemStack(ImmutableList<ItemStack> itemStack) {
        if ((itemStack.size() > 0))
            return itemStack.get((int) (System.currentTimeMillis() / 1000L % itemStack.size()));
        else
            return ItemStack.EMPTY;
    }

    //Source is https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/client/core/helper/RenderHelper.java

    public void renderTooltip(int x, int y, List<String> tooltipData, int color, int color2) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 100);
        GlStateManager.disableDepth();
        boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
        if (lighting)
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        if (!tooltipData.isEmpty()) {
            int var5 = 0;
            int var6;
            int var7;
            FontRenderer fontRenderer = mc().fontRenderer;
            for (var6 = 0; var6 < tooltipData.size(); ++var6) {
                var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
                if (var7 > var5)
                    var5 = var7;
            }
            var6 = x + 12;
            var7 = y - 12;
            int var9 = 8;
            if (tooltipData.size() > 1)
                var9 += 2 + (tooltipData.size() - 1) * 10;
            float z = 300F;
            drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
            drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
            int var12 = (color & 0xFFFFFF) >> 1 | color & -16777216;
            drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, color, var12);
            drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, color, var12);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
            drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

            for (int var13 = 0; var13 < tooltipData.size(); ++var13) {
                String var14 = tooltipData.get(var13);
                fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
                if (var13 == 0)
                    var7 += 2;
                var7 += 10;
            }
        }

        if (!lighting)
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        GlStateManager.enableDepth();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
    }

    public static void drawGradientRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
        float var7 = (par5 >> 24 & 255) / 255F;
        float var8 = (par5 >> 16 & 255) / 255F;
        float var9 = (par5 >> 8 & 255) / 255F;
        float var10 = (par5 & 255) / 255F;
        float var11 = (par6 >> 24 & 255) / 255F;
        float var12 = (par6 >> 16 & 255) / 255F;
        float var13 = (par6 >> 8 & 255) / 255F;
        float var14 = (par6 & 255) / 255F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator var15 = Tessellator.getInstance();
        var15.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        var15.getBuffer().pos(par3, par2, z).color(var8, var9, var10, var7).endVertex();
        var15.getBuffer().pos(par1, par2, z).color(var8, var9, var10, var7).endVertex();
        var15.getBuffer().pos(par1, par4, z).color(var12, var13, var14, var11).endVertex();
        var15.getBuffer().pos(par3, par4, z).color(var12, var13, var14, var11).endVertex();
        var15.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    @Override
    public Rectangle area() {
        return itemArea;
    }

    @Override
    public void click() {
        BookApi.findLink(new ItemStackMapKey(cycleItemStack(itemStack)))
                .ifPresent(GuiScreenEMBook.instance::setCurrentPage);
    }
}
