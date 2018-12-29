package ru.mousecray.endmagic.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.client.model.ModelAltar;
import ru.mousecray.endmagic.tileentity.TileAltar;

@SideOnly(Side.CLIENT)
public class RendererTileAltar extends TileEntitySpecialRenderer<TileAltar> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(EndMagicData.ID, "textures/blocks/altar.png");
	private final ModelAltar altar = new ModelAltar();
	
	@Override
	public void render(TileAltar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        
        this.bindTexture(TEXTURE);
		
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		if (destroyStage < 0) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		}

		GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		
		//Moving cube
		altar.Shape7.rotationPointY = te.getFloat();

		altar.renderAll();
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}