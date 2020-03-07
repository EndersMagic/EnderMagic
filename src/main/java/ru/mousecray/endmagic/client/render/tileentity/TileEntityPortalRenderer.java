package ru.mousecray.endmagic.client.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.tileentity.portal.TilePortal;

@SideOnly(Side.CLIENT)
public class TileEntityPortalRenderer extends TileEntitySpecialRenderer<TilePortal> {

    private TileEntitySpecialRenderer<TileEntityEndPortal> vanilaRender = (TileEntitySpecialRenderer<TileEntityEndPortal>) TileEntityRendererDispatcher.instance.renderers.get(TileEntityEndPortal.class);

    private TileEntityEndPortal teEndPortal = new TileEntityEndPortal() {
        @Override
        public boolean shouldRenderFace(EnumFacing p_184313_1_) {
            return true;
        }
    };

    @Override
	public void render(TilePortal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        vanilaRender.render(teEndPortal, x, y, z, partialTicks, destroyStage, alpha);
    }
}