package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.entity.EntityPortal;

import javax.annotation.Nullable;

public class RenderEntityPortal extends Render<EntityPortal> {

    private TileEntityEndPortalRenderer vanilaRender = new TileEntityEndPortalRenderer();

    private TileEntityEndPortal teEndPortal = new TileEntityEndPortal() {
        @Override
        public boolean shouldRenderFace(EnumFacing p_184313_1_) {
            return true;
        }
    };

    public RenderEntityPortal(RenderManager renderManager) {
        super(renderManager);
        vanilaRender.setRendererDispatcher(TileEntityRendererDispatcher.instance);
    }

    @Override
    public void doRender(EntityPortal entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1, entity.getHeight(), 1);
        vanilaRender.render(teEndPortal, x - 0.5, y, z - 0.5, partialTicks, 0, 1);
        GlStateManager.popMatrix();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPortal entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityPortal livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }
}
